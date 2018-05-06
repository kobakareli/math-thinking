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
                        <input class="answer" type="radio" name="answer" value="1"> <span class="answer-1">{{ $task->{'option_1_' . App::getLocale()} }}<span><br>
                        <input class="answer" type="radio" name="answer" value="2"> <span class="answer-2">{{ $task->{'option_2_' . App::getLocale()} }}</span><br>
                        <input class="answer" type="radio" name="answer" value="3"> <span class="answer-3">{{ $task->{'option_3_' . App::getLocale()} }}</span><br>
                        <input class="answer" type="radio" name="answer" value="4"> <span class="answer-4">{{ $task->{'option_4_' . App::getLocale()} }}</span>
                    </div>
                @else
                    <div class="closed fs-16">
                        <input class="answer" type="number" name="answer" placeholder="{{ trans('web.type_answer') }}"></input>
                    </div>
                @endif

                <div class="submit-answer-button fs-17" data-task="{{ $task->id }}" data-user="{{ Auth::user()->id }}">
                    {{ trans('web.submit') }}
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
