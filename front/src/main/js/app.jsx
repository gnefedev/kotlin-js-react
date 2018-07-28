import 'babel-polyfill';
import React from 'react';
import {render} from 'react-dom';
import {BrowserRouter} from 'react-router-dom';
import Home from "./Home";
import Switch from "react-router-dom/es/Switch";
import Route from "react-router-dom/es/Route";

window.onload = () => {
    render(
        <BrowserRouter>
            <Switch>
                <Route path="/" component={Home}/>
            </Switch>
        </BrowserRouter>,
        document.getElementById('react')
    );
};
