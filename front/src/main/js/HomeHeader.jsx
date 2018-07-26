import React, {PropTypes} from 'react';
import {Dropdown} from "primereact/components/dropdown/Dropdown";

const HomeHeader = ({brands, brand, onBrandChange, colors, color, onColorChange}) => (
    <div>
        Brand:
        <Dropdown
            value={brand}
            options={toOptions(brands)}
            onChange={(e) => onBrandChange(e.value)}/>
        Color:
        <Dropdown
            value={color}
            options={toOptions(colors)}
            onChange={(e) => onColorChange(e.value)}/>
    </div>
);

function toOptions(values) {
    let options = values.map((value) => {
        return {
            label: value,
            value: value
        }
    });
    options.unshift({
        label: "all",
        value: ""
    });
    return options;
}

HomeHeader.propTypes = {
    brands: PropTypes.array.isRequired,
    brand: PropTypes.string,
    onBrandChange: PropTypes.func.isRequired,
    colors: PropTypes.array.isRequired,
    color: PropTypes.string,
    onColorChange: PropTypes.func.isRequired
};

export default HomeHeader;
