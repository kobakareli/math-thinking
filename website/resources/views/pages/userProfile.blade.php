@extends('layouts.user')

@section('styles')
    <link href="{{ asset('css/user.css') }}" rel="stylesheet">
@endsection

@section('content')
<div class="central-content">
    <div class="share-div">
        <p class="share fs-18">{{ trans('web.share_page') }}:</p>
        <img class="fb-button" src="/images/fb.png">
    </div>

    <span class="mail-text fs-20">{{ trans('web.email') }}:</span>
    <span class="mail fs-18">{{ $user->email }}</span>
    <br>
    <span class="name-text fs-20">{{ trans('web.name') }}:</span>
    <span class="name fs-18">{{ $user->name }}</span>
    <br>
    <span class="level-text fs-20">{{ trans('web.level') }}:</span>
    <span class="level fs-18">{{ $user->level }}</span>
    <br>

    @if(count($user->tasksHistory) > 0)
        <p class="table-title fs-20">{{ trans('web.tasks_history') }}</p>
        <div class="table-wrapper">
            <table class="tasks-history">
                <tr>
                    <th class="date-row">{{ trans('web.date') }}</th>
                    <th class="title-row">{{ trans('web.title') }}</th>
                    <th class="answer-row">{{ trans('web.answer') }}</th>
                    <th class="status-row">{{ trans('web.status') }}</th>
                </tr>
                @foreach($user->tasksHistory as $entry)
                    <tr>
                        <td>{{ \Carbon\Carbon::parse($entry->pivot->created_at)->addHours(4) }}</td>
                        <td>{{ $entry->{'title_' . App::getLocale()} }}</td>
                        <td>{{ $entry->pivot->submitted_answer }}</td>
                        <td>{{ $entry->pivot->status }}</td>
                    </tr>
                @endforeach
            </table>
        </div>
    @endif


    @if(count($user->testsHistory) > 0)
        <p class="table-title fs-20">{{ trans('web.tests_history') }}</p>
        <div class="table-wrapper">
            <table class="users-history">
                <tr>
                    <th class="date-row">{{ trans('web.date') }}</th>
                    <th class="title-row">{{ trans('web.title') }}</th>
                    <th class="status-row">{{ trans('web.status') }}</th>
                    <th class="score-row">{{ trans('web.score') }}</th>
                </tr>
                @foreach($user->testsHistory as $entry)
                    <tr>
                        <td>{{ \Carbon\Carbon::parse($entry->pivot->created_at)->addHours(4) }}</td>
                        <td>{{ $entry->{'title_' . App::getLocale()} }}</td>
                        <td>{{ $entry->pivot->status }}</td>
                        <td>{{ $entry->pivot->score }}</td>
                    </tr>
                @endforeach
            </table>
        </div>
    @endif
</div>
@endsection
