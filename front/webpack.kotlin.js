var merge = require("webpack-merge");
var path = require("path");

module.exports = merge(require("./webpack.common.js"), {
    entry: path.resolve(__dirname, "src/main/index.kotlin.js"),
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
});
