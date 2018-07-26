import React, { PropTypes } from 'react';
import {DataTable} from "primereact/components/datatable/DataTable";
import {Column} from "primereact/components/column/Column";

const HomeContent = ({ cars }) => (
    <DataTable value={cars}>
        <Column header="Brand" field="brand"/>
        <Column header="Color" body={(rowData) => <span style={{color: rowData['color']}}>{rowData['color']}</span>}/>
        <Column header="Year" field="year"/>
    </DataTable>
);

HomeContent.propTypes = {
    cars: PropTypes.array.isRequired,
};

export default HomeContent;
