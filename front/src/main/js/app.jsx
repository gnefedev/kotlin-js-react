import 'babel-polyfill';
import React from 'react';
import { render } from 'react-dom';
import Home from 'Home'

window.onload = () => {
    render(
        <Home/>,
        document.getElementById('react')
    );
};
