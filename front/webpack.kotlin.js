var webpack = require("webpack");
var path = require("path");

module.exports = {
    entry: path.resolve(__dirname, "src/main/index.kotlin.js"),
    output: {
        path: path.resolve(__dirname, "build"),
        filename: "bundle.js"
    },
    resolve: {
        modules: [
            path.resolve(__dirname, "node_modules"),
            path.resolve(__dirname, "build/kotlin-js-min/main/")
        ]
    },
    devtool: "inline-source-map",
    plugins: [
        new webpack.HotModuleReplacementPlugin(),
      new webpack.DefinePlugin({
        'process.env': {
          NODE_ENV: JSON.stringify('production')
        }
      }),
      new webpack.optimize.UglifyJsPlugin()
    ],
    devServer: {
        contentBase: "./build/web/",
        port: 9000,
        hot: true,
        historyApiFallback: {
            index: "/index.html"
        },
        proxy: [
            {
                context: ["/api"],
                target: "http://localhost:8080",
                ws: true
            }
        ]
    }
};
