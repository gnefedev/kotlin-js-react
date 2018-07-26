import 'babel-polyfill';
import React from 'react';
import {render} from 'react-dom';
import {BrowserRouter} from 'react-router-dom';
import routes from './routes';

window.onload = () => {
    render(
        <BrowserRouter>
            {routes}
        </BrowserRouter>,
        document.getElementById('react')
    );
};
