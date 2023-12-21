import React, { MouseEvent, FormEvent } from "react";
import './Poly.css'

type Props = {
};
type State = {
    numberOfBlocks: string;
    svgWidth: string;
    svgHeight: string;
    freePolys: number[][][];
    blockSize: string;
};

const POLY_ENDPOINT: string = 'http://localhost:8080/poly/enumerate/';

class Poly extends React.Component<Props, State> {

    state = {
        numberOfBlocks: "",
        svgWidth: "100",
        svgHeight: "100",
        freePolys: [],
        blockSize: "50"
    }
    handleChange = (e: FormEvent<HTMLInputElement>): void => {
        this.setState({ numberOfBlocks: e.currentTarget.value });

    };

    handleClick = (e: MouseEvent<HTMLButtonElement>): void => {
        e.preventDefault();
        fetch(POLY_ENDPOINT + this.state.numberOfBlocks, {
            method: "GET",
            mode: "cors",
            headers: {
                "Accept": "application/json"
            }
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                this.setState({ freePolys: data.polys })
                let size = parseInt(this.state.blockSize) * (parseInt(this.state.numberOfBlocks) + 1);
                this.setState({ svgWidth: size.toString() })
                this.setState({ svgHeight: size.toString() })
            })
            .catch((err) => {
                console.log(err.message);
            });
    }

    translateX = (r: number, rowSize: number) => {
        let res = this.translate(r, rowSize, parseInt(this.state.svgWidth))
        console.log("Translate X", r, res);
        return res;
    }

    translateY = (c: number, colSize: number) => {
        let res = this.translate(c, colSize, parseInt(this.state.svgHeight))
        console.log("Translate Y", c, res)
        return res;
    }

    translate = (i: number, size: number, dimension: number) => {
        let blockSize = parseInt(this.state.blockSize);
        let iMid = Math.floor(size / 2);
        if (size % 2 != 0) {
            return (dimension / 2 - blockSize / 2) + (i - iMid) * blockSize;
        } else {
            return dimension / 2 + (i - iMid) * blockSize
        }
    }

    print = (r: number, c: number) => {
        console.log("row", r, "column", c);
        return 0;
    }

    render() {
        return (
            <>
                <div className="polyStyle">
                    <div className="headerStyle">
                        <label>
                            Number Of Blocks:
                            <input type="text" value={this.state.numberOfBlocks} onChange={this.handleChange}></input>
                        </label>
                        <button onClick={this.handleClick}>Enumerate</button>
                    </div>
                    <div className="bodyStyle">
                        {
                            this.state.freePolys.map((poly: number[][], index) => (
                                <div key={index}>
                                    <svg width={this.state.svgWidth} height={this.state.svgHeight} className="svgStyle">
                                        {
                                            poly.map((row: number[], rowIndex) => (
                                                row.map((col: number, colIndex: number) => (
                                                    col == 1 && <rect width={this.state.blockSize} height={this.state.blockSize}
                                                                x={this.translateX(rowIndex, poly.length)} 
                                                                y={this.translateY(colIndex, row.length)}
                                                                fill="green" strokeWidth="3" stroke="rgb(0,0,0)" />
                                                ))
                                            ))
                                        }
                                    </svg>
                                </div>

                            ))
                        }
                    </div>
                </div>
            </>
        )
    }

}

export default Poly;