@extends('layouts.template')

@section('styles')
    <link href="{{ asset('css/task.css') }}" rel="stylesheet">
    <link href="{{ asset('css/test.css') }}" rel="stylesheet">
@endsection

@section('content')
    <div class="test-page task-page">

        <p class="title fs-28">{{ $test->{'title_' . App::getLocale()} }}</p>

        @if(isset($status))
            <div class="status-box @if($status == 1){{ 'done' }}@endif">
                <span class="status complete fs-24">{{ trans('web.complete') }}</span>
                <span class="status incomplete fs-24">{{ trans('web.incomplete') }}</span>
                <span class="score fs-24">{{ $score . '/' . count($test->tasks) }}</span>
            </div>
        @endif

        <form method="POST" action="{{url('/submit/test/' . $test->id)}}" class="tasks">
            {{ csrf_field() }}
            @foreach($test->tasks as $indx=>$task)
                <div class="task @if($indx == 0){{ 'active first' }} @else{{ 'next' }}@endif @if($indx == count($test->tasks) - 1){{ 'last' }}@endif">
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
                                    <input filled="false" class="answer" type="radio" name="{{ 'answer-' . $task->id }}" value="1" required> <span class="answer-1">{{ $task->{'option_1_' . App::getLocale()} }}</span><br>
                                    <input filled="false" class="answer" type="radio" name="{{ 'answer-' . $task->id }}" value="2"> <span class="answer-2">{{ $task->{'option_2_' . App::getLocale()} }}</span><br>
                                    <input filled="false" class="answer" type="radio" name="{{ 'answer-' . $task->id }}" value="3"> <span class="answer-3">{{ $task->{'option_3_' . App::getLocale()} }}</span><br>
                                    <input filled="false" class="answer" type="radio" name="{{ 'answer-' . $task->id }}" value="4"> <span class="answer-4">{{ $task->{'option_4_' . App::getLocale()} }}</span>
                                </div>
                            @else
                                <div class="closed fs-16">
                                    <input filled="false" class="answer" type="text" name="{{ 'answer-' . $task->id }}" placeholder="{{ trans('web.type_answer') }}" required></input>
                                </div>
                            @endif
                        </div>
                    @endif
                </div>
            @endforeach
            <input type="submit" class="submit-form-button disabled fs-17" value="{{ trans('web.submit') }}">
            </input>
            <p class="fs-18">{{ trans('web.submit_warn') }}</p>
        </form>

        <div class="fb-comments" data-href="{{ url('/' . App::getLocale() . '/test/' . $test->id) }}" data-numposts="5"></div>
    </div>
@endsection

@section('scripts')
    <script type="text/javascript" src="{{ asset('js/task.js') }}"></script>
    <script type="text/javascript" src="{{ asset('js/test.js') }}"></script>
@endsection
