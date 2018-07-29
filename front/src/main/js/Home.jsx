import React, {PropTypes} from 'react';
import {DataTable} from "primereact/components/datatable/DataTable";
import {Column} from "primereact/components/column/Column";
import {Dropdown} from "primereact/components/dropdown/Dropdown";

class Home
  extends React.Component {


  state = {
    loaded: false,
    color: searchAsMap(
      this.props.location.search
    )["color"],
    brand: searchAsMap(
      this.props.location.search
    )["brand"]
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
  this.navigateToChanged({brand})
    }
    colors={this.state.colors}
    color={this.state.color}
    onColorChange={color =>
  this.navigateToChanged({color})
    }
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
  }) {
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
    brand,
    color
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

export default Home;

//render part
const HomeHeader = ({
brands,
brand,
onBrandChange,
colors,
color,
onColorChange
}) => (
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

HomeHeader.propTypes = {
  brands: PropTypes
    .array.isRequired,
  brand: PropTypes.string,
  onBrandChange: PropTypes
    .func.isRequired,
  colors: PropTypes
    .array.isRequired,
  color: PropTypes.string,
  onColorChange: PropTypes
    .func.isRequired
};

const HomeContent = ({
 cars
}) => (
  <DataTable value={cars}>
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

HomeContent.propTypes = {
  cars: PropTypes
    .array.isRequired,
};

//Layout
const Layout = ({
  children
}) => (
  <div className={"wrapper"}>
    {children}
  </div>
);

Layout.propTypes = {
  children: PropTypes
    .any.isRequired
};

const Header = ({
  children
}) => (
  <div className={"header"}>
    {children}
  </div>
);

Header.propTypes = {
  children: PropTypes.object
};

const Content = ({
 children
}) => (
  <div className={"content"}>
    {children}
  </div>
);

Content.propTypes = {
  children: PropTypes
    .object.isRequired
};

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
