@extends('layouts.template')

@section('styles')
    <link href="{{ asset('css/task.css') }}" rel="stylesheet">
    <link href="{{ asset('css/test.css') }}" rel="stylesheet">
@endsection

@section('content')
    <div class="test-page task-page">

        <p class="title fs-28">{{ $test->{'title_' . App::getLocale()} }}</p>

        <form method="POST" action="" class="tasks">
            @foreach($test->tasks as $indx=>$task)
                <div class="task @if($indx == 0){{ 'active first' }}@endif @if($indx == count($test->tasks) - 1){{ 'last' }}@endif">
                    <div class="title fs-28">
                        <div class="prev active"></div>
                        {{ trans('web.task') . ' ' . ($indx + 1) }}
                        <div class="next active"></div>
                    </div>
                    <div class="collapsible c-description active fs-18">{{ trans('web.description') }}</div>
                    <div class="description content active fs-18">
                        {!! $task->{'description_' . App::getLocale()} !!}
                    </div>
                    @if($task->{'hint_' . App::getLocale()} != null)
                        <div class="collapsible c-hint fs-18">{{ trans('web.hint') }}</div>
                        <div class="hint content fs-18">
                            {!! $task->{'hint_' . App::getLocale()} !!}
                        </div>
                    @endif
                    @if($task->{'answer_' . App::getLocale()} != null)
                        <div class="collapsible c-answer fs-18">{{ trans('web.solution') }}</div>
                        <div class="answer content fs-18">
                            {!! $task->{'answer_' . App::getLocale()} !!}
                        </div>
                    @endif

                    @if(Auth::check())
                        <div class="submit-answer">
                            <p class="fs-18">{{ trans('web.submit_answer') }}</p>
                            @if($task->has_options)
                                <div class="open fs-16">
                                    <input class="answer" type="radio" name="answer" value="1"> <span class="answer-1" required>{{ $task->{'option_1_' . App::getLocale()} }}<span><br>
                                    <input class="answer" type="radio" name="answer" value="2"> <span class="answer-2">{{ $task->{'option_2_' . App::getLocale()} }}</span><br>
                                    <input class="answer" type="radio" name="answer" value="3"> <span class="answer-3">{{ $task->{'option_3_' . App::getLocale()} }}</span><br>
                                    <input class="answer" type="radio" name="answer" value="4"> <span class="answer-4">{{ $task->{'option_4_' . App::getLocale()} }}</span>
                                </div>
                            @else
                                <div class="closed fs-16">
                                    <input class="answer" type="number" name="answer" placeholder="{{ trans('web.type_answer') }}" required></input>
                                </div>
                            @endif
                        </div>
                    @endif
                </div>
            @endforeach
            <input type="submit" class="submit-form-button fs-17" value="{{ trans('web.submit') }}">
            </input>
        </form>
    </div>
@endsection

@section('scripts')
    <script type="text/javascript" src="{{ asset('js/task.js') }}"></script>
    <script type="text/javascript" src="{{ asset('js/test.js') }}"></script>
@endsection
