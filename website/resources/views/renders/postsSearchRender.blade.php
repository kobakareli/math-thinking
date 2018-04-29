@if(count($tasks) > 0)
    <div class="task-results result" data-url="{{ url('/') . '/' . App::getLocale() . '/ajax/search/' }}">
        <p class="title fs-22">{{ trans('web.problems') }}:</p>
        <div class="posts">
            <a href="{{ url('/') . '/' . App::getLocale() . '/tasks/' . $tasks[0]->id}}">
                <div class="post small next carousel-slide">

                    <div class="post-texts">
                        <p class="offer-category fs-14">
                            @if(count($tasks[0]->categories) > 0)
                                {{ $tasks[0]->categories[0]->{'title_' . App::getLocale()} }}
                            @endif
                        </p>
                        <p class="offer-title fs-17">
                            {{ $tasks[0]->{'title_' . App::getLocale()} }}
                        </p>
                        <span class="description fs-16">
                            @if(strlen(strip_tags($tasks[0]->{'description_' . App::getLocale()})) > 145)
                                {{ mb_substr(strip_tags($tasks[0]->{'description_' . App::getLocale()}), 0, 145, 'utf-8') }}...
                            @else
                                {{ strip_tags($tasks[0]->{'description_' . App::getLocale()}) }}
                            @endif
                        </span>
                    </div>
                </div>
            </a>
        </div>
        <a href="{{ url('/' . App::getLocale() . '/search/') }}" class="see-more fs-16">{{ trans('web.see_more') }}</a>
    </div>
@endif
@if(count($articles) > 0)
    <div class="article-results result" data-url="{{ url('/') . '/' . App::getLocale() . '/ajax/search/' }}">
        <p class="title fs-22">{{ trans('web.articles') }}:</p>
        <div class="posts">

            <a href="{{ url('/') . '/' . App::getLocale() . '/articles/' . $articles[0]->id}}">
                <div class="post small next carousel-slide">

                    <div class="post-texts">
                        <p class="offer-category fs-14">
                            @if(count($articles[0]->categories) > 0)
                                {{ $articles[0]->categories[0]->{'title_' . App::getLocale()} }}
                            @endif
                        </p>
                        <p class="offer-title fs-17">
                            {{ $articles[0]->{'title_' . App::getLocale()} }}
                        </p>
                        <span class="desciption fs-16">
                            @if(strlen(strip_tags($articles[0]->{'text_' . App::getLocale()})) > 145)
                                {{ mb_substr(strip_tags($articles[0]->{'text_' . App::getLocale()}), 0, 145, 'utf-8') }}...
                            @else
                                {{ strip_tags($articles[0]->{'text_' . App::getLocale()}) }}
                            @endif
                        </span>
                    </div>
                </div>
            </a>

        </div>
        <a href="{{ url('/' . App::getLocale() . '/search/') }}" class="see-more fs-16">{{ trans('web.see_more') }}</a>
    </div>
@endif
@if(count($tests) > 0)
    <div class="test-results result" data-url="{{ url('/') . '/' . App::getLocale() . '/ajax/search/' }}">
        <p class="title fs-22">{{ trans('web.tests') }}:</p>
        <div class="posts">
            <a href="{{ url('/') . '/' . App::getLocale() . '/tests/' . $tests[0]->id}}">
                <div class="post small next carousel-slide">

                    <div class="post-texts">
                        <p class="offer-category fs-14">
                            @if(count($tests[0]->categories) > 0)
                                {{ $tests[0]->categories[0]->{'title_' . App::getLocale()} }}
                            @endif
                        </p>
                        <p class="offer-title fs-17">
                            {{ $tests[0]->{'title_' . App::getLocale()} }}
                        </p>
                    </div>
                </div>
            </a>
        </div>
        <a href="{{ url('/' . App::getLocale() . '/search/') }}" class="see-more fs-16">{{ trans('web.see_more') }}</a>
    </div>
@endif
