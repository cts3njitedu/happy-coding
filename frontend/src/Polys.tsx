import { MouseEvent, FormEvent, useState, useRef } from "react";
import { GrRotateRight } from "react-icons/gr";
import { TbFlipHorizontal, TbFlipVertical } from "react-icons/tb";
import './Polys.scss'
import { FixedSizeList as List } from 'react-window';
import ndjsonStream from "can-ndjson-stream";
import { HiArrowSmDown, HiArrowSmRight } from "react-icons/hi";

type State = {
    freePolys: number[][][];
    numberOfBlocks: string;
    numberOfPolys: string;
    blockSize: string;
    inputDisabled: boolean;
    polysId: any;
};

const POLY_ENDPOINT_ENUMERATE: string = '/api/poly/enumerate';

const DEFAULT_BLOCK_SIZE = "50";

function Polys() {
    const [polyState, setPolyState] = useState<State>({
        freePolys: [],
        blockSize: DEFAULT_BLOCK_SIZE,
        numberOfBlocks: "",
        numberOfPolys: "",
        inputDisabled: false,
        polysId: ""
    })
    const [sessionId] = useState(() => crypto.randomUUID());
    const handleClick = (numberOfBlocks: number): void => {
        setPolyState({
            ...polyState,
            freePolys: [],
            blockSize: DEFAULT_BLOCK_SIZE,
            numberOfBlocks: "",
            numberOfPolys: "",
            inputDisabled: true,
            polysId: ""
        })
        if (numberOfBlocks < 16) {
            callEnumerateApi(POLY_ENDPOINT_ENUMERATE, { numberOfBlocks, sessionId }, {
                "Accept": "application/x-ndjson",
                "Content-Type": "application/json"
            })
                .then(response => ndjsonStream(response.body))
                .then((polyStream) => {
                    const reader = polyStream.getReader();
                    let read: any;
                    reader.read().then(read = (result: any) => {
                        if (result.done) {
                            setPolyState((prevState) => ({
                                ...prevState,
                                inputDisabled: false
                            }))
                            return;
                        }
                        let data = result.value;
                        setPolyState((prevState) => ({
                            ...prevState,
                            polysId: data.polysId,
                            freePolys: [...prevState.freePolys, ...enrichFreePolys(data.freePolys)],
                            numberOfBlocks: data.numberOfBlocks,
                            numberOfPolys: data.numberOfPolys
                        }))
                        reader.read().then(read);
                    })
                })
        }
    }
    const enableInput = (): void => {
        setPolyState((prevState) => ({
            ...prevState,
            inputDisabled: false
        }))
    }
    const changeBlockSize = (newBlockSize: string): void => {
        setPolyState({
            ...polyState,
            blockSize: newBlockSize
        })
    }
    return (
        <>
            <div className="polyClass">

                <PolyHead handleClick={handleClick} inputDisabled={polyState.inputDisabled} />
                {polyState.numberOfPolys.length != 0 &&
                    <>
                        <PolySubHead
                            numberOfBlocks={polyState.numberOfBlocks}
                            numberOfPolys={polyState.numberOfPolys}
                            blockSize={polyState.blockSize}
                            changeBlockSize={changeBlockSize} />
                        <PolyBody
                            blockSize={polyState.blockSize}
                            freePolys={polyState.freePolys}
                            numberOfBlocks={polyState.numberOfBlocks}
                            numberOfPolys={polyState.numberOfPolys}
                            polysId={polyState.polysId}
                            enableInput={enableInput}
                            inputDisabled={polyState.inputDisabled}
                        />

                    </>}
            </div>
        </>
    )

}

const VALID_NUMBER_OF_BLOCKS = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15];

function PolyHead(props: any) {
    const [numberOfBlocks, setNumberOfBlocks] = useState("");
    const handleChange = (e: FormEvent<HTMLSelectElement>): void => {
        setNumberOfBlocks(e.currentTarget.value);
    }
    const handleClick = (e: MouseEvent<HTMLButtonElement>): void => {
        e.preventDefault();
        props.handleClick(numberOfBlocks);
    }
    return (
        <>
            <div className="headerClass">
                <label>
                    Number Of Blocks:
                    <select name="numberOfBlocks" disabled={props.inputDisabled} onChange={handleChange} defaultValue="0">
                        <option value="none"></option>
                        {
                            VALID_NUMBER_OF_BLOCKS.map((block: number) => (
                                <option key={block} value={block}>{block}</option>
                            ))
                        }
                    </select>
                </label>
                <button disabled={props.inputDisabled} onClick={handleClick}>Enumerate</button>
            </div>
        </>
    )
}
const callEnumerateApi = (uri: string, req: any, headers: any) => {
    return fetch(uri, {
        method: "POST",
        // mode: "cors",
        headers: headers,
        body: JSON.stringify(req)
    });
}

function PolySubHead(props: any) {
    const isDisplay = props.numberOfBlocks && props.numberOfPolys;
    const changeBlockSize = (e: FormEvent<HTMLInputElement>): void => {
        if (e.code == 'Enter') {
            props.changeBlockSize(e.currentTarget.value);
        }
    }
    return (
        <>
            {isDisplay && <div className="subHeaderClass">
                <p>Number Of Blocks: {props.numberOfBlocks}</p>
                <p>Number Of Free Polyominoes: {props.numberOfPolys}</p>
                <label>
                    Block Size:
                    <input type="text" defaultValue={props.blockSize} onKeyDown={changeBlockSize}></input>
                </label>
            </div>}
        </>
    )
}


const translate = (i: number, size: number, dimension: number, blockSize: number) => {
    let iMid = Math.floor(size / 2);
    if (size % 2 != 0) {
        return (dimension / 2 - blockSize / 2) + (i - iMid) * blockSize;
    } else {
        return dimension / 2 + (i - iMid) * blockSize
    }
}

const cellKey = (polysId: any, numberOfBlocks: string, polyIndex: number, rowIndex: number, colIndex: number) => {
    return [polysId, numberOfBlocks, polyIndex, rowIndex, colIndex].toString();
}

const divKey = (polysId: any, numberOfBlocks: string, polyIndex: number): string => {
    return [polysId, numberOfBlocks, polyIndex].toString();
}

const svgKey = (divKey: string) => {
    return ["svg", divKey].toString();
}

const enrichFreePolys = (polys: number[][][]): any[] => {
    return polys.map((poly: number[][]) => (
        {
            id: crypto.randomUUID(),
            matrix: poly
        }
    ))
}

enum PolyBodyButtonState {
    RIGHT = 0,
    LEFT,
    UP,
    DOWN
}
function PolyBody(props: any) {
    const svgWidth = parseInt(props.blockSize) * (parseInt(props.numberOfBlocks) + 1);
    const svgHeight = parseInt(props.blockSize) * (parseInt(props.numberOfBlocks) + 1);
    const [widthMultiplier, setWidthMultiplier] = useState(() => props.numberOfBlocks >= 4 ? 5 : 1)
    const [heightMultiplier, setHeightMultiplier] = useState(1);
    const freePolys = props.freePolys;
    const [isHorizontal, setIsHorizontal] = useState(true);
    const [buttonGroupInfos, setButtonGroupInfos] = useState([
        {
            direction: PolyBodyButtonState.RIGHT,
            style: {
                backgroundColor: "blue"
            },
            icon: <HiArrowSmRight />
        },
        {
            direction: PolyBodyButtonState.DOWN,
            style: {
                backgroundColor: "white"
            },
            icon: <HiArrowSmDown />
        },
    ])
    const handleDirectionChange = (e: FormEvent<HTMLButtonElement>) => {
        e.preventDefault();
        let buttonId = parseInt(e.currentTarget.id);
        console.log("Button id:", buttonId);
        setButtonGroupInfos(() => buttonGroupInfos.map(buttonInfo => {
            let newButtonInfo = {
                ...buttonInfo
            }
            if (buttonInfo.direction === buttonId) {
                if (PolyBodyButtonState.RIGHT === buttonId) {
                    setWidthMultiplier(props.numberOfBlocks >= 4 ? 5 : 1);
                    setHeightMultiplier(1);
                    setIsHorizontal(true); 
                } else {
                    setHeightMultiplier(props.numberOfBlocks >= 4 ? 5 : 1);
                    setWidthMultiplier(1);
                    setIsHorizontal(false); 
                }
                newButtonInfo = {
                    ...newButtonInfo,
                    style: {
                        ...newButtonInfo.style,
                        backgroundColor: "blue"
                    }
                }
            } else {
                newButtonInfo = {
                    ...newButtonInfo,
                    style: {
                        ...newButtonInfo.style,
                        backgroundColor: "white"
                    }
                }
            }
            return newButtonInfo;
        }));
    }
    return (
        <>
            <div className="virtualListButtonClass">
                {
                    buttonGroupInfos.map(buttonInfo => {
                        return <button
                            key={PolyBodyButtonState[buttonInfo.direction]}
                            id={buttonInfo.direction.toString()}
                            style={buttonInfo.style}
                            onClick={handleDirectionChange}>{buttonInfo.icon}
                        </button>
                    })
                }
            </div>
            <div className="bodyClass">
                {
                    <List
                        height={heightMultiplier*svgHeight}
                        itemCount={props.numberOfPolys}
                        itemSize={svgWidth}

                        layout={isHorizontal ? "horizontal" : "vertical"}
                        className="virtualListClass"
                        width={widthMultiplier * svgWidth}
                        itemData={{
                            freePolys: props.freePolys,
                            blockSize: props.blockSize,
                            numberOfBlocks: props.numberOfBlocks, polysId: props.polysId, svgHeight, svgWidth
                        }}
                        itemKey={(index) => {
                            return freePolys[index].id;
                        }}>
                        {Poly}
                    </List>
                }
            </div>
        </>
    )
}


function Poly(props: any) {
    const svgWidth = props.data.svgWidth;
    const svgHeight = props.data.svgHeight;
    const blockSize = parseInt(props.data.blockSize);
    const divStyle = {
        height: svgHeight * 1.2,
        width: svgWidth,
        ...props.style
    }
    const freePoly = props.data.freePolys[props.index];
    const poly: number[][] = freePoly.matrix;
    const index: number = freePoly.id;
    const [fill, setFill] = useState(genHexString());
    const [svgRotate, setSvgRotate] = useState("rotate(0), scale(1,1)");
    const [degree, setDegree] = useState(0);
    const [scaleX, setScaleX] = useState(1);
    const [scaleY, setScaleY] = useState(1);
    const svgKeyRef = useRef(null);
    const handleChangeColor = (e: FormEvent<HTMLInputElement>): void => {
        setFill(e.currentTarget.value)
    }
    const handleRotate = (): void => {
        let deg = (degree + 90) % 360;
        setDegree(deg)
        setSvgRotate("rotate(" + deg + ") " + "scale(" + scaleX + "," + scaleY + ")");
    }
    const handleFlipY = (): void => {
        let sy = -1 * scaleY;
        setScaleY(sy);
        setSvgRotate("rotate(" + degree + "), " + "scale(" + scaleX + "," + sy + ")");
    }
    const handleFlipX = (): void => {
        let sx = -1 * scaleX;
        setScaleX(sx);
        setSvgRotate("rotate(" + degree + "), " + "scale(" + sx + "," + scaleY + ")");

    }
    const handleFlipVertically = (e: MouseEvent<HTMLButtonElement>): void => {
        e.preventDefault();
        if (degree == 0 || degree == 180) {
            handleFlipX();
        } else {
            handleFlipY();
        }
    }
    const handleFlipHorizontally = (e: MouseEvent<HTMLButtonElement>): void => {
        e.preventDefault();
        if (degree == 0 || degree == 180) {
            handleFlipY();
        } else {
            handleFlipX();
        }
    }
    return (
        <>
            <div className="polyDiv" style={divStyle}>
                <svg
                    ref={svgKeyRef}
                    id={svgKey(divKey(props.data.polysId, props.data.numberOfBlocks, index))}
                    width={svgWidth} height={svgHeight}
                    className="svgClass"
                    transform={svgRotate}
                    transform-origin="center">
                    {
                        poly.map((row: number[], rowIndex) => (
                            row.map((col: number, colIndex: number) => (
                                col == 1 && <rect
                                    key={cellKey(props.data.polysId, props.data.numberOfBlocks, index, rowIndex, colIndex)}
                                    width={props.data.blockSize}
                                    height={props.data.blockSize}
                                    x={translate(rowIndex, poly.length, svgWidth, blockSize)}
                                    y={translate(colIndex, row.length, svgHeight, blockSize)}
                                    fill={fill}
                                    strokeWidth="10"
                                    stroke="rgb(0,0,0)"
                                    className="rectClass" />
                            ))
                        ))
                    }
                </svg>
                <div>
                    <input type="color" defaultValue={fill} onChange={handleChangeColor}></input>
                    {parseInt(props.data.numberOfBlocks) > 1 && <div>
                        <button onClick={handleRotate}><GrRotateRight /></button>
                        <button onClick={handleFlipVertically}><TbFlipVertical /></button>
                        <button onClick={handleFlipHorizontally}><TbFlipHorizontal /></button>
                    </div>}
                </div>
            </div>
        </>
    )

}

const genHexString = () => {
    const hex = '0123456789ABCDEF';
    let output: string = '#';
    for (let i = 1; i <= 6; i++) {
        output += hex.charAt(Math.floor(Math.random() * hex.length));
    }
    return output;
}

export default Polys;