import { MouseEvent, FormEvent, useState, useRef, useEffect, useCallback } from "react";
import { GrRotateRight } from "react-icons/gr";
import { TbFlipHorizontal, TbFlipVertical, TbRulerOff } from "react-icons/tb";
import './Polys.scss'
import ReactPaginate from "react-paginate";

type State = {
    freePolys: number[][][];
    numberOfBlocks: string;
    numberOfPolys: string;
    blockSize: string;
    inputDisabled: boolean;
    polysId: any;
    polysPerPage: number;
    isFinishEnumerating: boolean
};

const POLY_ENDPOINT: string = '/api/poly/getFreePolys';

const DEFAULT_POLYS_PER_PAGE: number = 20;

function Polys() {
    const [polyState, setPolyState] = useState<State>({
        freePolys: [],
        blockSize: "50",
        numberOfBlocks: "",
        numberOfPolys: "",
        inputDisabled: false,
        polysId: "",
        polysPerPage: DEFAULT_POLYS_PER_PAGE,
        isFinishEnumerating: false
    })
    enum FreePolyState {
        START, FINISH
    }
    const [sessionId] = useState(() => crypto.randomUUID());

    const [client, setClient] = useState(() => {
        const sock = new SockJS("http://localhost:5173/poly-websocket", null, {
            timeout: 2000
        });
        const client = Stomp.over(sock);
        return client;
    });

    useEffect(() => {
        client.connect({}, function (frame: any) {
            console.log('Connected: ' + frame);
            client.subscribe('/user/queue/poly', function (message: any) {
                let data = JSON.parse(message.body);
                console.log("Number Of Polys:", data.numberOfPolys, "Number Of Blocks:", data.numberOfBlocks);
                console.log(data);
                setPolyState((prevState) => ({
                    ...prevState,
                    polysId: data.polysId,
                    numberOfPolys: data.numberOfPolys,
                    numberOfBlocks: data.numberOfBlocks
                }))
                callEnumerateApi(POLY_ENDPOINT, {
                    ...data,
                    polysPerPage: DEFAULT_POLYS_PER_PAGE,
                    page: 1
                })
                    .then((data) => {
                        console.log("I am here", data);
                        setPolyState((prevState) => ({
                            ...prevState,
                            freePolys: enrichFreePolys(data.freePolys),
                            inputDisabled: false,
                            isFinishEnumerating: true
                        }))
                    })
                    .catch((err) => {
                        console.error("Error:", err);
                        setPolyState((prevState) => ({
                            ...prevState,
                            inputDisabled: false,
                            isFinishEnumerating: true
                        }))
                    })

            });
        });
    }, [])

    // useEffect(() => {
    //     let event = new EventSource("/api/poly/queue/enumerate/" + sessionId)
    //     event.onmessage = (e) => {
    //         console.log("Message:", e.data);
    //         let data = JSON.parse(e.data);
    //         let temp = polyState.freePolys;
    //         console.log("Temp:", temp);
    //         if (data.freePolyState == FreePolyState[FreePolyState.FINISH]) {
    //             setPolyState((prevState) => ({
    //                 ...prevState,
    //                 inputDisabled: false
    //             }))
    //         } else {
    //             setPolyState((prevState) => ({
    //                 ...prevState,
    //                 polysId: data.polysId,
    //                 numberOfBlocks: data.numberOfBlocks,
    //                 numberOfPolys: data.numberOfPolys,
    //                 polysPerPage: DEFAULT_POLYS_PER_PAGE,
    //                 freePolys: [...prevState.freePolys, ...enrichFreePolys(data.freePolys)],
    //                 count: prevState.count + 1,
    //                 subPolysId: crypto.randomUUID()

    //             }))
    //         }

    //     };
    //     event.onopen = (e) => {
    //         console.log("Opened:", e);
    //     }
    //     event.onerror = (e) => {
    //         console.log("Error")
    //     }
    //     return () => event.close();
    // }, [])

    const handleClick = (numberOfBlocks: number): void => {
        setPolyState({
            ...polyState,
            freePolys: [],
            blockSize: "50",
            numberOfBlocks: "",
            numberOfPolys: "",
            inputDisabled: true,
            polysId: "",
            polysPerPage: DEFAULT_POLYS_PER_PAGE,
            isFinishEnumerating: false
        })
        client.send('/app/poly', {}, JSON.stringify({
            numberOfBlocks,
            sessionId
        }))

        // if (numberOfBlocks < 16) {
        //     callEnumerateApi({ numberOfBlocks, sessionId }, sessionId, setPolyState);
        // }
    }
    const enableInput = (): void => {
        setPolyState((prevState) => ({
            ...prevState,
            inputDisabled: false
        }))
    }
    const handlePolysPerPageChange = (polysPerPage: number): void => {
        callEnumerateApi(POLY_ENDPOINT, {
            polysId: polyState.polysId,
            polysPerPage,
            page: 1
        })
            .then((data) => {
                setPolyState((prevState) => ({
                    ...prevState,
                    freePolys: enrichFreePolys(data.freePolys),
                    polysPerPage: polysPerPage
                }))
            })
            .catch((err) => {
                console.error("Error:", err);
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
                            polysPerPage={polyState.polysPerPage}
                            handlePolysPerPageChange={handlePolysPerPageChange} />
                        <PolyBody
                            blockSize={polyState.blockSize}
                            freePolys={polyState.freePolys}
                            numberOfBlocks={polyState.numberOfBlocks}
                            numberOfPolys={polyState.numberOfPolys}
                            polysId={polyState.polysId}
                            enableInput={enableInput}
                            polysPerPage={polyState.polysPerPage}
                            inputDisabled={polyState.inputDisabled}
                            isFinishEnumerating={polyState.isFinishEnumerating}
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
        // setNumberOfBlocks("");
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
const callEnumerateApi = (uri: string, req: any) => {
    return fetch(uri, {
        method: "POST",
        // mode: "cors",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify(req)
    })
        .then((response) => {
            if (!response.ok) {
                let err = new Error("HTTP status code: " + response.status)
                throw err
            }
            return response.json();
        })
        .catch((err) => {
            console.log("Error", err.message);
        })
}

function PolySubHead(props: any) {
    const isDisplay = props.numberOfBlocks && props.numberOfPolys;
    const handlePolysPerPageChange = (e: FormEvent<HTMLInputElement>): void => {
        if (e.code == 'Enter') {
            props.handlePolysPerPageChange(parseInt(e.currentTarget.value));
        }
    }
    return (
        <>
            {isDisplay && <div className="subHeaderClass">
                <p>Number Of Blocks: {props.numberOfBlocks}</p>
                <p>Number Of Free Polyominoes: {props.numberOfPolys}</p>
                <label>
                    Free Polyominoes Per Page:
                    <input
                        type="number"
                        defaultValue={props.polysPerPage}
                        maxLength={4}
                        max="1000"
                        min="1"
                        onKeyDown={handlePolysPerPageChange}
                    ></input>
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
    return polys.map((poly: number[][], index: number) => (
        {
            id: crypto.randomUUID(),
            matrix: poly
        }
    ))
}

type PolyBodyType = {
    data: any[],
    offset: number,
    numberPerPage: number,
    pageCount: number,
    selectedPage: number,
    currentData: any[]
}
function PolyBody(props: any) {
    const [pagination, setPagination] = useState<PolyBodyType>({
        data: props.freePolys,
        offset: 0,
        numberPerPage: props.polysPerPage,
        pageCount: 0,
        selectedPage: 0,
        currentData: [],
    })
    useEffect(() => {
        if (props.isFinishEnumerating) {
            console.log("We are marshal", props.polysPerPage, props.freePolys);
            setPagination((prevState) => ({
                ...prevState,
                data: props.freePolys,
                currentData: props.freePolys,
                selectedPage: 0
            }))
        }

    }, [props.isFinishEnumerating])
    useEffect(() => {
        console.log("We are marshall");
        setPagination((prevState) => ({
            ...prevState,
            pageCount: Math.ceil(props.numberOfPolys / prevState.numberPerPage),
            currentData: prevState.data
        }))
    }, [pagination.numberPerPage])
    const handlePageClick = (event: any) => {
        handlePageChange(event.selected, pagination.numberPerPage)
    }
    const handlePageChange = (page: number, numberPerPage: number) => {
        callEnumerateApi(POLY_ENDPOINT, {
            polysId: props.polysId,
            polysPerPage: numberPerPage,
            page: page + 1
        })
            .then((data) => {
                const selected = page;
                let enrichedPolys = enrichFreePolys(data.freePolys);
                setPagination({
                    ...pagination,
                    selectedPage: selected,
                    data: enrichedPolys,
                    currentData: enrichedPolys
                })
            })
            .catch((err) => {
                console.error("Error:", err);
            })
    }
    return (
        <>
            <div className="polyPagination">
                {pagination.currentData.length != 0 && <ReactPaginate
                    previousLabel={'Prev'}
                    nextLabel={'Next'}
                    breakLabel={'...'}
                    pageCount={pagination.pageCount}
                    marginPagesDisplayed={2}
                    pageRangeDisplayed={5}
                    onPageChange={handlePageClick}
                    containerClassName={'pagination'}
                    activeClassName={'paginationActive'}
                    pageClassName={'paginationPage'}
                    nextClassName={'paginationNext'}
                    previousClassName={'paginationPrevious'}
                    forcePage={pagination.selectedPage}
                />}
            </div>

            <div className="bodyClass">
                {
                    pagination.currentData && pagination.currentData.map((poly: { matrix: number[][], id: number }) => (
                        <Poly
                            key={divKey(props.polysId, props.numberOfBlocks, poly.id)}
                            blockSize={props.blockSize}
                            numberOfBlocks={props.numberOfBlocks}
                            poly={poly.matrix}
                            index={poly.id}
                            polysId={props.polysId}
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
                    id={svgKey(divKey(props.polysId, props.numberOfBlocks, index))}
                    width={svgWidth} height={svgHeight}
                    className="svgClass"
                    transform={svgRotate}
                    transform-origin="center">
                    {
                        poly.map((row: number[], rowIndex) => (
                            row.map((col: number, colIndex: number) => (
                                col == 1 && <rect
                                    key={cellKey(props.polysId, props.numberOfBlocks, index, rowIndex, colIndex)}
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
                    {parseInt(props.numberOfBlocks) > 1 && <div>
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