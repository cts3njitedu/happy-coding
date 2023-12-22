import React, { MouseEvent, FormEvent, useState, useEffect } from "react";
import './Polys.css'

type Props = {
};
type State = {
    freePolys: number[][][];
    numberOfBlocks: string;
    numberOfPolys: string;
    blockSize: string;
    inputDisabled: boolean;
};

const POLY_ENDPOINT: string = 'http://localhost:8080/poly/enumerate/';

class Polys extends React.Component<Props, State> {

    state = {
        freePolys: [],
        blockSize: "50",
        numberOfBlocks: "",
        numberOfPolys: "",
        inputDisabled: false
    }

    handleClick = (numberOfBlocks: number): void => {
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
                    freePolys: data.polys,
                    numberOfBlocks: data.numberOfBlocks,
                    numberOfPolys: data.numberOfPolys
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
                <div className="polyStyle">
                    <PolyHead handleClick={this.handleClick} inputDisabled={this.state.inputDisabled} />
                    <PolySubHead
                        numberOfBlocks={this.state.numberOfBlocks}
                        numberOfPolys={this.state.numberOfPolys} />
                    <PolyBody
                        blockSize={this.state.blockSize}
                        freePolys={this.state.freePolys}
                        numberOfBlocks={this.state.numberOfBlocks}
                        numberOfPolys={this.state.numberOfPolys}
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
            <div className="headerStyle">
                <label>
                    Number Of Blocks:
                    <input type="text" value={numberOfBlocks} onChange={handleChange}></input>
                </label>
                <button onClick={handleClick}>Enumerate</button>
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

const cellKey = (polyIndex: number, rowIndex: number, colIndex: number) => {
    return [polyIndex, rowIndex, colIndex].toString();
}

function PolyBody(props: any) {
    const svgWidth = parseInt(props.blockSize) * (parseInt(props.numberOfBlocks) + 1);
    const svgHeight = parseInt(props.blockSize) * (parseInt(props.numberOfBlocks) + 1);
    const blockSize = parseInt(props.blockSize);
    return (
        <>
            <div className="bodyStyle">
                {
                    props.freePolys.map((poly: number[][], index: number) => (
                        <div key={index}>
                            <svg key={index} width={svgWidth} height={svgHeight} className="svgStyle">
                                {
                                    poly.map((row: number[], rowIndex) => (
                                        row.map((col: number, colIndex: number) => (
                                            col == 1 && <rect key={cellKey(index, rowIndex, colIndex)} width={props.blockSize} height={props.blockSize}
                                                x={translate(rowIndex, poly.length, svgWidth, blockSize)}
                                                y={translate(colIndex, row.length, svgHeight, blockSize)}
                                                fill="blue" strokeWidth="7" stroke="rgb(0,0,0)" />
                                        ))
                                    ))
                                }
                            </svg>

                        </div>
                    ))
                }
            </div>
        </>
    )




}

export default Polys;