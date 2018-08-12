// @flow
import React from 'react';
import {DataTable} from "primereact/components/datatable/DataTable";
import {Column} from "primereact/components/column/Column";
import {Dropdown} from "primereact/components/dropdown/Dropdown";
import type { ContextRouter } from 'react-router';

type Car = {
  brand: string,
  color: string,
  year: number
}

type NavigateToChanged = {
  brand?: string,
  color?: string
}

class Home
  extends React.Component
    <ContextRouter, State>{



  state = {
    loaded: false,
    color: searchAsMap(
      this.props.location.search
    )["color"],
    brand: searchAsMap(
      this.props.location.search
    )["brand"],
    cars: [],
    brands: [],
    colors: []
  };



  render() {
    if (!this.state.loaded)
      return null;
    return (
      <Layout>
        <Header>
          <HomeHeader
    brands={this.state.brands}
    brand={this.state.brand}
    onBrandChange={brand =>
  this.navigateToChanged({
    brand
  })}
    colors={this.state.colors}
    color={this.state.color}
    onColorChange={color =>
  this.navigateToChanged({
    color
  })}
          />
        </Header>
        <Content>
          <HomeContent
    cars={this.state.cars}
          />
        </Content>
      </Layout>
    );
  }

  navigateToChanged({
    brand = this.state.brand,
    color = this.state.color
  }: Object) {
    this.props.history.push(
"?brand=" + (brand?brand:"")
+ "&color=" + (color?color:""))
  }

  async componentDidMount()
  {
    this.props.history.listen(
      location => {
        let query = searchAsMap(
          location.search
        );
        this.setState({
          brand: query["brand"],
          color: query["color"]
        });

        this.loadData(
          query["brand"],
          query["color"]
        )

      });

    this.setState({
      brands: await (
        await fetch('/api/brands')
      ).json(),

      colors: await (
        await fetch('/api/colors')
      ).json()

    });

    await this.loadData(
      this.state.brand,
      this.state.color
    );
  }


  async loadData(
    brand?: string,
    color?: string
  ) {
    let url = '/api/cars?' +
'brand=' + (brand?brand:"") +
"&color=" + (color?color:"");
    this.setState({
      cars: await (
        await fetch(url)
      ).json(),

      loaded: true
    });
  }
}

type State = {
  color?: string,
  brand?: string,

  loaded: boolean,
  cars: Array<Car>,
  brands: Array<string>,
  colors: Array<string>
};

export default Home;

type HomeHeaderProps = {
brands: Array<string>,
brand?: string,
onBrandChange: (string) => void,
colors: Array<string>,
color?: string,
onColorChange: (string) => void
}

//render part
const HomeHeader = ({
brands,
brand,
onBrandChange,
colors,
color,
onColorChange
}: HomeHeaderProps) => (
  <div>
    Brand:
    <Dropdown
      value={brand}
      onChange={e =>
        onBrandChange(e.value)
      }
      options={withDefault("all",
        brands.map(value => ({
      label: value, value: value
    })))}

    />
    Color:
    <Dropdown
      value={color}
      onChange={e =>
        onColorChange(e.value)
      }
      options={withDefault("all",
        colors.map(value => ({
      label: value, value: value
    })))}

    />
  </div>
);

const HomeContent = (props: {
   cars: Array<Car>
}) => (
  <DataTable value={props.cars}>
    <Column header="Brand"
            body={rowData =>
      rowData["brand"]
            }/>
    <Column header="Color"
            body={rowData =>
      <span
    style={{
      color: rowData['color']
    }}>
        {rowData['color']}
      </span>
  }/>
    <Column header="Year"
            body={rowData =>
      rowData["year"]}
    />
  </DataTable>
);

//Layout
const Layout = (props: {
    children: any
}) => (
  <div className={"wrapper"}>
    {props.children}
  </div>
);

const Header = (props: {
    children?: any
}) => (
  <div className={"header"}>
    {props.children}
  </div>
);

const Content = (props: {
   children: any
}) => (
  <div className={"content"}>
    {props.children}
  </div>
);

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

function withDefault(
  label, options
) {
  options.unshift({
    label: label, value: null
  });
  return options;
}
