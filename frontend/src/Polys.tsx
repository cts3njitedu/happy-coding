import React, { MouseEvent, FormEvent, useState, useRef } from "react";
import { GrRotateRight } from "react-icons/gr";
import { TbFlipHorizontal, TbFlipVertical } from "react-icons/tb";
import './Polys.css'

type Props = {
};
type State = {
    freePolys: number[][][];
    numberOfBlocks: string;
    numberOfPolys: string;
    blockSize: string;
    inputDisabled: boolean;
    polyId: any;
};

const POLY_ENDPOINT: string = 'http://localhost:8080/poly/enumerate/';

class Polys extends React.Component<Props, State> {

    state = {
        freePolys: [],
        blockSize: "50",
        numberOfBlocks: "",
        numberOfPolys: "",
        inputDisabled: false,
        polyId: ""
    }
    handleClick = (numberOfBlocks: number): void => {
        this.setState({
            freePolys: [],
            blockSize: "50",
            numberOfBlocks: "",
            numberOfPolys: "",
            inputDisabled: true,
            polyId: ""
        })
        fetch(POLY_ENDPOINT + numberOfBlocks, {
            method: "GET",
            mode: "cors",
            headers: {
                "Accept": "application/json"
            }
        })
            .then((response) => response.json())
            .then((data) => {
                this.setState({
                    polyId: crypto.randomUUID(),
                    freePolys: data.polys,
                    numberOfBlocks: data.numberOfBlocks,
                    numberOfPolys: data.numberOfPolys,
                    inputDisabled: false
                })
            })
            .catch((err) => {
                console.log(err.message);
            });
    }
    enableInput = (): void => {
        this.setState({
            inputDisabled: false
        })
    }
    render() {
        return (
            <>
                <div className="polyClass">
                    <PolyHead handleClick={this.handleClick} inputDisabled={this.state.inputDisabled} />
                    <PolySubHead
                        numberOfBlocks={this.state.numberOfBlocks}
                        numberOfPolys={this.state.numberOfPolys} />
                    <PolyBody
                        blockSize={this.state.blockSize}
                        freePolys={this.state.freePolys}
                        numberOfBlocks={this.state.numberOfBlocks}
                        numberOfPolys={this.state.numberOfPolys}
                        polyId={this.state.polyId}
                        enableInput={this.enableInput} />
                </div>
            </>
        )
    }

}

function PolySubHead(props: any) {
    const isDisplay = props.numberOfBlocks && props.numberOfPolys;
    return (
        <>
            {isDisplay && <div className="subHeaderClass">
                <p>Number Of Blocks: {props.numberOfBlocks}</p>
                <p>Number Of Free Polyominoes: {props.numberOfPolys}</p>
            </div>}
        </>
    )
}

function PolyHead(props: any) {
    const [numberOfBlocks, setNumberOfBlocks] = useState("");
    const handleChange = (e: FormEvent<HTMLInputElement>): void => {
        setNumberOfBlocks(e.currentTarget.value);
    }
    const handleClick = (e: MouseEvent<HTMLButtonElement>): void => {
        e.preventDefault();
        setNumberOfBlocks("");
        props.handleClick(numberOfBlocks);
    }
    return (
        <>
            <div className="headerClass">
                <label>
                    Number Of Blocks:
                    <input type="text" disabled={props.inputDisabled} value={numberOfBlocks} onChange={handleChange}></input>
                </label>
                <button disabled={props.inputDisabled} onClick={handleClick}>Enumerate</button>
            </div>
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

const cellKey = (polyId: any, numberOfBlocks: string, polyIndex: number, rowIndex: number, colIndex: number) => {
    return [polyId, numberOfBlocks, polyIndex, rowIndex, colIndex].toString();
}

const divKey = (polyId: any, numberOfBlocks: string, polyIndex: number) => {
    return [polyId, numberOfBlocks, polyIndex].toString();
}

const svgKey = (divKey: string) => {
    return ["svg", divKey].toString();
}

function PolyBody(props: any) {
    return (
        <>
            <div className="bodyClass">
                {
                    props.freePolys.map((poly: number[][], index: number) => (
                        <Poly
                            key={divKey(props.polyId, props.numberOfBlocks, index)}
                            blockSize={props.blockSize}
                            numberOfBlocks={props.numberOfBlocks}
                            poly={poly}
                            index={index}
                            polyId={props.polyId}
                        />
                    ))
                }
            </div>
        </>
    )
}

function Poly(props: any) {
    const svgWidth = parseInt(props.blockSize) * (parseInt(props.numberOfBlocks) + 1);
    const svgHeight = parseInt(props.blockSize) * (parseInt(props.numberOfBlocks) + 1);
    const blockSize = parseInt(props.blockSize);
    const index = props.index;
    const divStyle = {
        height: svgHeight * 1.2,
        width: svgWidth
    }
    const poly: number[][] = props.poly;
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
                    id={svgKey(divKey(props.polyId, props.numberOfBlocks, index))}
                    width={svgWidth} height={svgHeight}
                    className="svgClass"
                    transform={svgRotate}
                    transform-origin="center">
                    {
                        poly.map((row: number[], rowIndex) => (
                            row.map((col: number, colIndex: number) => (
                                col == 1 && <rect
                                    key={cellKey(props.polyId, props.numberOfBlocks, index, rowIndex, colIndex)}
                                    width={props.blockSize}
                                    height={props.blockSize}
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
                    {props.numberOfBlocks != "1" && <div>
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