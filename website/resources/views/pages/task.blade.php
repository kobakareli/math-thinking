@extends('layouts.template')

@section('styles')
    <link href="{{ asset('css/task.css') }}" rel="stylesheet">
@endsection

@section('content')
    <div class="task-page">

        <p class="title fs-28">{!! $task->{'title_' . App::getLocale()} !!}</p>
        <button class="collapsible c-description active fs-18">{{ trans('web.description') }}</button>
        <div class="description content active fs-18">
            {!! $task->{'description_' . App::getLocale()} !!}
        </div>
        @if($task->{'hint_' . App::getLocale()} != null)
            <button class="collapsible c-hint fs-18">{{ trans('web.hint') }}</button>
            <div class="hint content fs-18">
                {!! $task->{'hint_' . App::getLocale()} !!}
            </div>
        @endif
        @if($task->{'answer_' . App::getLocale()} != null)
            <button class="collapsible c-answer fs-18">{{ trans('web.solution') }}</button>
            <div class="answer content fs-18">
                {!! $task->{'answer_' . App::getLocale()} !!}
            </div>
        @endif

        @if(Auth::check())
            <div class="submit-answer">
                <p class="fs-18">{{ trans('web.submit_answer') }}</p>
                @if($task->has_options)
                    <div class="open fs-16">
                        <input class="answer" type="radio" name="answer" value="1"> <span class="answer-1">{{ $task->{'option_1_' . App::getLocale()} }}</span></input><br>
                        <input class="answer" type="radio" name="answer" value="2"> <span class="answer-2">{{ $task->{'option_2_' . App::getLocale()} }}</span></input><br>
                        <input class="answer" type="radio" name="answer" value="3"> <span class="answer-3">{{ $task->{'option_3_' . App::getLocale()} }}</span></input><br>
                        <input class="answer" type="radio" name="answer" value="4"> <span class="answer-4">{{ $task->{'option_4_' . App::getLocale()} }}</span></input>
                    </div>
                @else
                    <div class="closed fs-16">
                        <input class="answer" name="answer" placeholder="{{ trans('web.type_answer') }}"></input>
                    </div>
                @endif

                <div class="submit-answer-button fs-17" data-task="{{ $task->id }}" data-user="{{ Auth::user()->id }}">
                    {{ trans('web.submit') }}
                </div>
            </div>
        @else
            <p class="login-required fs-18">{{ trans('web.registration_required') }}</p>
        @endif

        @if(count($articles) > 0)
            <div class="trending article-tasks">
                <span class="blog-section-title fs-20">{{ trans('web.learn_more') }}</span>
                @if($morearticles)
                    <a href="{{ url('/' . App::getLocale() . '/search/articles/-1/' . $article->categories[0]->id) }}" class="see-more-link fs-20">{{ trans('web.see_more') }}</a>
                @endif
                <div class="trends">
                    @foreach($articles as $article)
                        <a href="{{ url('/' . App::getLocale() . '/article/' . $article->id) }}">
                            <div class="trend">

                                <p class="title fs-16">
                                    {{ $article->{'title_' . App::getLocale()} }}
                                </p>
                                <div class="info">
                                    <span class="date fs-15">
                                        {{ \Carbon\Carbon::parse(explode(" ",$article->created_at)[0])->format('d/m/Y') }}
                                    </span>
                                </div>
                            </div>
                        </a>
                    @endforeach
                </div>
            </div>
        @endif


        <div class="fb-comments" data-href="{{ url('/' . App::getLocale() . '/task/' . $task->id) }}" data-numposts="5"></div>

        @if(Auth::check())
            <div class="response-popup">
                <div class="popup-filter"></div>
                <div class="correct">
                    <img class="status-image" src="/images/check.png"/>
                    <p class="status-text fs-18">{{ trans('web.correct_answer') }}</p>
                    <div class="level-up">
                        <p class="level-text fs-18">
                            {{ trans('web.level_up') }}
                        </p>
                        <div class="flex-container">
                            <p class="prev-level fs-20">{{ Auth::user()->level - 1 }}</p>
                            <img class="next-level-image" src="/images/next-level.svg"/>
                            <p class="next-level fs-20">{{ Auth::user()->level }}</p>
                        </div>
                    </div>
                </div>
                <div class="incorrect">
                    <img class="status-image" src="/images/cross.png"/>
                    <p class="status-text fs-18">{{ trans('web.wrong_answer') }}</p>
                </div>
            </div>
        @endif

    </div>
@endsection

@section('scripts')
    <script type="text/javascript" src="{{ asset('js/task.js') }}"></script>
@endsection

@if(Auth::check())
    @push('scripts')
        <script>
            var user = @json(Auth::user()->id);
            var task = @json($task->id);
        </script>
    @endpush
@endif
