import React from 'react';
import { Switch, Route, IndexRoute } from 'react-router';

import Home from 'Home';

export default (
    <Switch>
        <Route path="/" component={Home}/>
    </Switch>
);
