<!doctype html>
<html lang="{{ app()->getLocale() }}">
    <head>
        <!-- Global site tag (gtag.js) - Google Analytics -->
        <script async src="https://www.googletagmanager.com/gtag/js?id=UA-122465332-1"></script>
        <script>
          window.dataLayer = window.dataLayer || [];
          function gtag(){dataLayer.push(arguments);}
          gtag('js', new Date());

          gtag('config', 'UA-122465332-1');
        </script>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">


        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">

        @if(isset($page_title))
            <title>{{ $page_title }} | Math Thinking</title>
            <meta class="og_title" property="og:title" content="{{ $page_title . ' | Math Thinking' }}">
        @else
            <title>Math Thinking</title>
            <meta class="og_title" property="og:title" content="Math Thinking">
        @endif

        <meta class="og_description" property="og:description" content="{{ trans('web.website_description') }}">

        <meta name="description"  content="{{ trans('web.website_description') }}" />

        <meta class="og_image" property="og:image" content="{{ url('/images/og.png') }}" id="meta_image">

        <meta property="og:type" content="website" />
        <meta property="fb:app_id" content="1026007677546401" />
        <meta property="fb:pages" content="" />

        <!-- Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Raleway:100,600" rel="stylesheet" type="text/css">

        <!-- CSRF Token -->
        <meta name="csrf-token" content="{{ csrf_token() }}">

        <!-- Styles -->
        <link href="{{ asset('css/app.css') }}" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet">

        <link href="{{ asset('css/main.css') }}" rel="stylesheet">
        <link href="{{ asset('css/fonts.css') }}" rel="stylesheet">

        <link href="{{ asset('css/picker.classic.css') }}" rel="stylesheet">
        <link href="{{ asset('css/picker.classic.date.css') }}" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">

        @yield('styles')
    </head>
    <body>

        <div id="fb-root"></div>
        <script>
            window.fbAsyncInit = function() {
                 FB.init({
                   appId      : '1026007677546401',
                   xfbml      : true,
                   version    : 'v2.3'
                 });
            };

           (function(d, s, id){
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id)) {return;}
                js = d.createElement(s); js.id = id;
                js.src = "//connect.facebook.net/en_US/sdk.js";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));
        </script>

        <section class="header">
            <div class="nav-container">
                <a class="logo-link fs-24" href="{{ url('/' . App::getLocale()) }}">
                    <img class="logo desktop active" src="{{ url('/images/logo.svg') }}" />
                    <img class="logo mobile" src="{{ url('/images/logo.svg') }}" />
                    <span><firstletter>M</firstletter>ath <firstletter>T</firstletter>hinking</span>
                </a>

                <div class="nav">

                    <a href="{{ url('/' . App::getLocale() . '/articles') }}">
                        <div class="nav-item offers fs-17"><p>{{ trans('web.articles') }}</p></div>
                    </a>
                    <a href="{{ url('/' . App::getLocale() . '/tasks') }}">
                        <div class="nav-item contact fs-17"><p>{{ trans('web.problems') }}</p></div>
                    </a>
                    <a href="{{ url('/' . App::getLocale() . '/tests') }}">
                        <div class="nav-item blog fs-17"><p>{{ trans('web.tests') }}</p></div>
                    </a>

                </div>

                <div class="header-buttons">
                    <div class="header-button language-switcher">
                        <div class="wrapper">
                            <?php
                                $base = url('/');
                                $path = request()->path();
                                $pref = substr($path, 0, 2);
                                if($pref == 'en' || $pref == 'ru' || $pref == 'ge') {
                                    $path = substr($path, 2);
                                    if(substr($path, 0, 1) == '/') {
                                        $path = substr($path, 1);
                                    }
                                }
                            ?>
                            <div class="lang en <?php if(app()->getLocale() == 'en' || app()->getLocale() == null) { echo 'active'; } ?>">
                                <a href="{{ $base . '/en/' . $path }}">
                                    <p class="lang-text fs-16">EN</p>
                                </a>
                            </div>
                            <div class="lang ge <?php if(app()->getLocale() == 'ge') { echo 'active'; } ?>">
                                <a href="{{ $base . '/ge/' . $path }}">
                                    <p class="lang-text fs-16">GE</p>
                                </a>
                            </div>
                        </div>
                    </div>

                    <div class="header-button search-button">
                    </div>
                </div>
            </div>
            <div class="search-wrapper search-id">
                <div class="black-filter">
                </div>
                <div class="search-container">
                    <div class="selects">
                        <select type="select" class="select" name="super-category" id="super-category">
                            @foreach($supercategories as $category)
                                <option value="{{ $category->id }}">{{ $category->{'title_' . App::getLocale()} }}</option>
                            @endforeach
                        </select>
                        <div class="categories-wrapper">
                            <select type="select" class="select" name="categories" id="categories">
                            </select>
                        </div>
                        <div class="dummy" style="display: none;"></div>
                        <input id="date-from" class="datepicker" type="date" name="date-from" placeholder="{{ trans('web.start_date') }}"/>
                        <input id="date-to" class="datepicker" type="date" name="date-to" placeholder="{{ trans('web.end_date') }}"/>
                    </div>
                    <div class="search-input">
                        <input class="fs-26" type="text" name="search" placeholder="{{ trans('web.search') }}"/>
                    </div>
                    <div class="results active">

                    </div>
                </div>
            </div>
        </section>

        @yield('content')

        @stack('scripts')
        <script type="text/javascript" src="{{ asset('js/jquery.min.js') }}"></script>
        <!--<script type="text/javascript" src="{{ asset('js/app.js') }}"></script>-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
        <script type="text/javascript" src="{{ asset('js/main.js') }}"></script>
        <script type="text/javascript" src="{{ asset('js/picker.js') }}"></script>
        <script type="text/javascript" src="{{ asset('js/picker.date.js') }}"></script>

        @yield('scripts')
    </body>
</html>
