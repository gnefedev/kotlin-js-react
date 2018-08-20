// @flow
import React from 'react';
import type { ContextRouter } from 'react-router';

class Home
  extends React.Component
    <ContextRouter, State>{



  state = {
    loaded: false, //(1)
    color: searchAsMap(
  this.props.location.search
    )["color"],
    brand: searchAsMap(
  this.props.location.search
    )["brand"],
    brands: [], //(2)
    colors: [] //(2)
  };


  async componentDidMount()
  {

    this.setState({ //(3)
      brands: await ( //(4)
    await fetch('/api/brands')
      ).json(),

      colors: await ( //(4)
    await fetch('/api/colors')
      ).json()

    });

  }
}

type State = {
  color?: string,
  brand?: string,

  loaded: boolean, //(1)
  brands: Array<string>, //(2)
  colors: Array<string> //(2)
};

export default Home;

//infrastructure
function searchAsMap(search) {
  if (search !== undefined
    && search.length > 1) {
    let result = {};
    search.substr(1)
      .split("&")
      .map((pairStr) =>
        pairStr.split("="))
      .forEach((pair) => {
        result[pair[0]] = pair[1]
      });
    return result
  } else {
    return {};
  }
}
