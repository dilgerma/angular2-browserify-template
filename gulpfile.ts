import * as gulp from "gulp";
import * as browserify from "browserify";
import * as watchify from "watchify"
import * as source from "vinyl-source-stream";
import * as gulpUtil from "gulp-util";
import * as  sourcemaps from "gulp-sourcemaps";
import * as  buffer from "vinyl-buffer";
import * as babelify from "babelify";


var tsify: any = require("tsify");
var sass = require('gulp-sass');
var watch = require('gulp-watch');
var embedTemplates = require('gulp-angular-embed-templates');



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

function browserifyInit(): any {
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

function configureTemplateInlining() {

// // ng2inlinetransform.js
var ng2TemplateParser = require('gulp-inline-ng2-template/parser');
var through = require('through2');
var options = {target: 'es5'};
//
function inlineTemplates(file:any) {
  return through(function (buf:any, enc:any, next:any){
    ng2TemplateParser({contents: buf, path: file}, options)((err:any, result:any) => {
      this.push(result);
      process.nextTick(next);
    });
  });
}
return inlineTemplates;
}


function configureTsify(browserifyLike: any) {
    var inlineTemplates = configureTemplateInlining();
    return browserifyLike
        .plugin(tsify, {
            module: "commonjs",
            target: "es6",
            path: "tsconfig.json",
            extensions: ['.ts']
        })
        .transform(inlineTemplates)
        .transform(babelify, {
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

gulp.task("build", ["css", "js"]);

gulp.task("css", () => {
    bundleCSS();
});

gulp.task("js", () => {
    bundleJS(bundler);
});

gulp.task("build:watch", () => {
    gulp.watch(
        [
            config.cssSrcPath + "/**/*.scss",
        ]
        , ['css']);

    var watchedBrowserify = configureTsify(watchify(browserifyInit()));
    bundleJS(watchedBrowserify);
    watchedBrowserify.on("update", () => bundleJS(watchedBrowserify));
    watchedBrowserify.on("log", gulpUtil.log);

});

var bundler = configureTsify(browserifyInit());

gulp.task("watch", ["build:watch"]);

gulp.task("default", ["build"]);




