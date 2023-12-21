import React, { MouseEvent, FormEvent } from "react";
import './Poly.css'

type Props = {
};
type State = {
    numberOfBlocks: string;
    svgWidth: string;
    svgHeight: string;
};

const POLY_ENDPOINT: string = 'http://localhost:8080/poly/enumerate/';

class Poly extends React.Component<Props, State> {

    state = {
        numberOfBlocks: "",
        svgWidth: "100",
        svgHeight: "100"
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
                let size = 100 * parseInt(this.state.numberOfBlocks);
                this.setState({ svgWidth: size.toString() })
                this.setState({ svgHeight: size.toString() })
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
                            <input type="text" value={this.state.numberOfBlocks} onChange={this.handleChange}></input>
                        </label>
                        <button onClick={this.handleClick}>Enumerate</button>
                    </div>
                    <div className="bodyStyle">

                    </div>
                </div>
            </>
        )
    }

}

export default Poly;