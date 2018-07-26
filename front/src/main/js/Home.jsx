import React from 'react';
import Layout from "./Layout";
import Content from "./Content";
import HomeContent from "./HomeContent";
import HomeHeader from "./HomeHeader";

class Home extends React.Component {
    state = {
        loaded: false,
        color: this.searchAsMap(this.props.location.search).color,
        brand: this.searchAsMap(this.props.location.search).brand
    };

    render() {
        if (!this.state.loaded) {
            return null;
        }
        return (
            <Layout>
                <HomeHeader
                    brands={this.state.brands}
                    brand={this.state.brand}
                    onBrandChange={(brand) => this.navigateToChanged(brand, this.state.color)}
                    colors={this.state.colors}
                    color={this.state.color}
                    onColorChange={(color) => this.navigateToChanged(this.state.brand, color)}
                />
                <Content>
                    <HomeContent cars={this.state.cars}/>
                </Content>
            </Layout>
        );
    }

    navigateToChanged(brand, color) {
        this.props.history.push("/?brand=" + (brand ? brand : "") + "&color=" + (color ? color : ""))
    }

    searchAsMap(search) {
        if (search !== undefined && search.length > 1) {
            let result = {};
            search.substr(1)
                .split("&")
                .map((pairStr) => pairStr.split("="))
                .forEach((pair) => {
                    result[pair[0]] = pair[1]
                });
            return result
        } else {
            return {};
        }
    }

    async componentDidMount() {
        this.props.history.listen((location) => {
            let query = this.searchAsMap(location.search);
            this.setState({
                color: query.color,
                brand: query.brand
            });
            this.loadData(query.brand, query.color)
        });
        await this.loadData(this.state.brand, this.state.color);

        const colors = await (await fetch('/api/colors')).json();
        const brands = await (await fetch('/api/brands')).json();
        this.setState({
            colors,
            brands,
            loaded: true
        });
    }

    async loadData(brand, color) {
        let url = '/api/cars?brand=' + (brand ? brand : "") + "&color=" + (color ? color : "");
        const cars = await (await fetch(url)).json();
        this.setState({
            cars
        });
    }
}

export default Home;
