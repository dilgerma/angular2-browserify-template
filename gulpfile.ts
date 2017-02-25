import * as gulp from "gulp";
import * as browserify from "browserify";
import * as source from "vinyl-source-stream";
import * as gulpUtil from "gulp-util";
import * as  sourcemaps from "gulp-sourcemaps";
import * as  buffer from "vinyl-buffer";
import * as babelify from "babelify";


var tsify: any = require("tsify");
var sass = require('gulp-sass');
var watch = require('gulp-watch');


const config = {
    tsPath: "src/main/typescript",
    mainPath: "src/main/typescript/main.ts",
    cssSrcPath: "src/main/sass",
    baseDir: ".",
    distFile: 'application.js',
    distDir: "build/resources/main/static/js",
    distCssDir: "build/resources/main/static/css",
    libDir: "build/resources/main/static/js",
    modulesDir: "node_modules"
};

function browerifyInit() {
    return browserify({
        basedir: config.baseDir,
        debug: true,
        entries: [config.mainPath],
        cache: {},
        extensions: ['.js', '.json', '.ts'],
        packageCache: {}
    })

}

function bundleJS(browserifyLike: any) {

    return browserifyLike
        .bundle()
        .on('error', function (err: any) {
            gulpUtil.log(gulpUtil.colors.bgRed("ERROR:"), " ", err.toString());
            // this.emit('end');
        })
        .pipe(source(config.distFile))
        .pipe(buffer())
        .pipe(sourcemaps.init({loadMaps: true}))
        // .pipe(uglify())
        .pipe(sourcemaps.write('./'))
        .pipe(gulp.dest(config.distDir));
}

function bundleCSS() {
    return gulp.src(config.cssSrcPath + "/*.scss")
        .pipe(sass({outputStyle: 'compressed'}).on('error', sass.logError))
        .pipe(gulp.dest(config.distCssDir))
}

function configureTsify(browserifyLike: any) {
    return browserifyLike
        .plugin(tsify, {
            module: "commonjs",
            target: "es6",
            path: "tsconfig.json",
            extensions: ['.ts']
        }).transform(babelify, {
            global: true,
            ignore: /\/node_modules\/(?!@angular\/)/,
            presets: [
                "es2015"
            ],
            plugins: [
                "angular2-annotations",
                "transform-decorators-legacy",
                "transform-class-properties",
                "transform-flow-strip-types"
            ],
            extensions: ['.js', '.json', '.ts']
        });
}

gulp.task("build", () => {
    bundleCSS();
    bundleJS(configureTsify(browerifyInit()));
});

gulp.task("build:watch", () => {
    return gulp.watch(
        [
            config.cssSrcPath + "/**/*.scss",
            config.tsPath + "/**/*.ts"
        ]
        , ['build']);
});

gulp.task("watch", ["build:watch"]);

gulp.task("default", ["build"]);



