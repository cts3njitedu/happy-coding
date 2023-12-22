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

const divKey = (numberOfBlocks: string, polyIndex: number) => {
    return [numberOfBlocks, polyIndex].toString();
}

function PolyBody(props: any) {
    return (
        <>
            <div className="bodyClass">
                {
                    props.freePolys.map((poly: number[][], index: number) => (
                        <Poly
                            key = {divKey(props.numberOfBlocks, index)}
                            blockSize={props.blockSize}
                            numberOfBlocks={props.numberOfBlocks}
                            poly={poly}
                            index={index}
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
        height: svgHeight * 1.20
    }
    const poly: number[][] = props.poly;
    const [fill, setFill] = useState(genHexString());
    const handleChangeColor = (e: FormEvent<HTMLInputElement>): void => {
        setFill(e.currentTarget.value)
    }
    return (
        <>
            <div className="polyDiv" style={divStyle}>
                <svg width={svgWidth} height={svgHeight} className="svgClass">
                    {
                        poly.map((row: number[], rowIndex) => (
                            row.map((col: number, colIndex: number) => (
                                col == 1 && <rect
                                    key={cellKey(index, rowIndex, colIndex)}
                                    width={props.blockSize}
                                    height={props.blockSize}
                                    x={translate(rowIndex, poly.length, svgWidth, blockSize)}
                                    y={translate(colIndex, row.length, svgHeight, blockSize)}
                                    fill={fill} strokeWidth="10" stroke="rgb(0,0,0)" className="rectClass" />
                            ))
                        ))
                    }
                </svg>
                <input type="color" defaultValue={fill} onChange={handleChangeColor}></input>
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