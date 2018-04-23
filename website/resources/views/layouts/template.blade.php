<!doctype html>
<html lang="{{ app()->getLocale() }}">
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">

        <title>Laravel | Laravel</title>

        <!-- Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Raleway:100,600" rel="stylesheet" type="text/css">

        <!-- CSRF Token -->
        <meta name="csrf-token" content="{{ csrf_token() }}">

        <!-- Styles -->
        <link href="{{ asset('css/app.css') }}" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet">

        <link href="{{ asset('css/main.css') }}" rel="stylesheet">

        @yield('styles')
    </head>
    <body>

        <div id="app">
            <section class="header">
                <div class="nav-container">
                    <a class="logo-link" href="{{ url('/' . App::getLocale()) }}">
                        <img class="logo desktop active" src="{{ url('/images/bog.svg') }}" />
                        <img class="logo mobile" src="{{ url('/images/bog-logo.svg') }}" />
                    </a>

                    <div class="nav">

                        <a href="{{ url('/' . App::getLocale() . '/articles') }}">
                            <div class="nav-item offers bog-headline-semibold fs-17"><p>Articles</p></div>
                        </a>
                        <a href="{{ url('/' . App::getLocale() . '/problems') }}">
                            <div class="nav-item contact bog-headline-semibold fs-17"><p>Problems</p></div>
                        </a>
                        <a href="{{ url('/' . App::getLocale() . '/tests') }}">
                            <div class="nav-item blog bog-headline-semibold fs-17"><p>Tests</p></div>
                        </a>
                        <!--<a href="{{ url('/' . App::getLocale() . '/about') }}">
                            <div class="nav-item about bog-headline-semibold fs-17"><p>{{ trans('web.about_nav') }}</p></div>
                        </a>-->

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
                                <div class="lang en <?php if(app()->getLocale() == 'en') { echo 'active'; } ?>">
                                    <a href="{{ $base . '/en/' . $path }}">
                                        <p class="lang-text bog-headline-semibold fs-16">EN</p>
                                    </a>
                                </div>
                                <div class="lang ge <?php if(app()->getLocale() == 'ge') { echo 'active'; } ?>">
                                    <a href="{{ $base . '/ge/' . $path }}">
                                        <p class="lang-text bog-headline-semibold fs-16">GE</p>
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
                        <div class="search-input">
                            <input class="bog-medium fs-26" type="text" name="search" placeholder="Search"/>
                            <!--<img class="search-icon" src="/images/large-search-icon.png"/>-->
                        </div>
                        <div class="results">

                        </div>
                    </div>
                </div>
            </section>

            <div class="left-content">
                <div class="login-form active">
                    <div class="input-parent">
                        <input type="text" name="email" placeholder="E-mail">
                    </div>

                    <div class="input-parent">
                        <input type="password" name="email" placeholder="Password">
                    </div>

                    <div class="sign-in-button">
                        Sign in
                    </div>

                    <a href="" class="register-link">
                        Register
                    </a>
                    <a href="" class="password-recovery-link">
                        Recover Password
                    </a>
                </div>
            </div>

            <div class="central-content">

            </div>

            <div class="right-content">
                <div class="trending">
                    <p class="blog-section-title bog-headline-bold fs-20">Trending Tasks</p>

                    <div class="trends">
                        <a href="">
                            <div class="trend">

                                <p class="title bog-headline-semibold fs-16">
                                    Test
                                </p>
                                <div class="info">
                                    <span class="category bog-headline-medium fs-15">
                                        Test
                                    </span>
                                    <div class="circle">
                                    </div>
                                    <span class="date bog-headline-medium fs-15">
                                        Test
                                    </span>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>

            @yield('content')
        </div>

        @stack('scripts')
        <script type="text/javascript" src="{{ asset('js/jquery.min.js') }}"></script>
        <!--<script type="text/javascript" src="{{ asset('js/app.js') }}"></script>-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>

        @yield('scripts')
    </body>
</html>
