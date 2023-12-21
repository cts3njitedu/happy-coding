import React, { MouseEvent, FormEvent } from "react";
import './Polys.css'

type Props = {
};
type State = {
    numberOfBlocks: string;
    numberOfBlocksInput: string;
    svgWidth: string;
    svgHeight: string;
    freePolys: number[][][];
    blockSize: string;
};

const POLY_ENDPOINT: string = 'http://localhost:8080/poly/enumerate/';

class Polys extends React.Component<Props, State> {

    state = {
        numberOfBlocks: "",
        svgWidth: "",
        svgHeight: "",
        freePolys: [],
        blockSize: "50",
        numberOfBlocksInput: ""
    }
    handleChange = (e: FormEvent<HTMLInputElement>): void => {
        this.setState({ numberOfBlocksInput: e.currentTarget.value });

    };

    handleClick = (e: MouseEvent<HTMLButtonElement>): void => {
        e.preventDefault();
        fetch(POLY_ENDPOINT + this.state.numberOfBlocksInput, {
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
                    numberOfBlocks: this.state.numberOfBlocksInput

                })
            })
            .catch((err) => {
                console.log(err.message);
            });
    }
    render() {
        return (
            <>
                <div className="polyStyle">
                    <div className="headerStyle">
                        <label>
                            Number Of Blocks:
                            <input type="text" value={this.state.numberOfBlocksInput} onChange={this.handleChange}></input>
                        </label>
                        <button onClick={this.handleClick}>Enumerate</button>
                    </div>
                    <PolyBody
                        blockSize={this.state.blockSize}
                        freePolys={this.state.freePolys}
                        numberOfBlocks={this.state.numberOfBlocks} />
                </div>
            </>
        )
    }

}




const translate = (i: number, size: number, dimension: number, blockSize: number) => {
    let iMid = Math.floor(size / 2);
    if (size % 2 != 0) {
        return (dimension / 2 - blockSize / 2) + (i - iMid) * blockSize;
    } else {
        return dimension / 2 + (i - iMid) * blockSize
    }
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
                                            col == 1 && <rect width={props.blockSize} height={props.blockSize}
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