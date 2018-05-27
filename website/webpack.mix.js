let mix = require('laravel-mix');

/*
 |--------------------------------------------------------------------------
 | Mix Asset Management
 |--------------------------------------------------------------------------
 |
 | Mix provides a clean, fluent API for defining some Webpack build steps
 | for your Laravel application. By default, we are compiling the Sass
 | file for the application as well as bundling up all the JS files.
 |
 */

 /*.js('resources/assets/js/app.js', 'public/js')
     js('resources/assets/js/admin.js', 'public/js')*/

mix.sass('resources/assets/sass/app.scss', 'public/css')
    .sass('resources/assets/sass/_variables.scss', 'public/css')
   .sass('resources/assets/sass/admin.scss', 'public/css')
   .sass('resources/assets/sass/main.scss', 'public/css')
   .sass('resources/assets/sass/fonts.scss', 'public/css')
   .sass('resources/assets/sass/tasks.scss', 'public/css')
   .sass('resources/assets/sass/task.scss', 'public/css')
   .sass('resources/assets/sass/test.scss', 'public/css')
   .sass('resources/assets/sass/users.scss', 'public/css')
   .sass('resources/assets/sass/user.scss', 'public/css');
