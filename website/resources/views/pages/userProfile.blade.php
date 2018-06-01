@php
    $user = Auth::user();
@endphp

@extends('layouts.user')

@section('styles')
    <link href="{{ asset('css/user.css') }}" rel="stylesheet">
@endsection

@section('content')
<div class="central-content">
    <p class="mail-text">E-mail:</p>
    <p class="mail">{{ $user->email }}</p>
    <p class="name-text">Name:</p>
    <p class="name">{{ $user->name }}</p>

    <table class="tasks-history" style="width:100%">
        <tr>
            <th>{{ trans('web.date') }}</th>
            <th>{{ trans('web.title') }}</th>
            <th>{{ trans('web.answer') }}</th>
            <th>{{ trans('web.status') }}</th>
        </tr>
        @foreach($user->tasksHistory as $entry)
            <tr>
                <td>{{ $entry->created_at }}</td>
                <td>{{ $entry->{'title_' . App::getLocale()} }}</td>
                <td>{{ $entry->pivot->submitted_answer }}</td>
                <td>{{ $entry->pivot->status }}</td>
            </tr>
        @endforeach
    </table>

    <table class="users-history" style="width:100%">
        <tr>
            <th>{{ trans('web.date') }}</th>
            <th>{{ trans('web.title') }}</th>
            <th>{{ trans('web.status') }}</th>
            <th>{{ trans('web.answer') }}</th>
        </tr>
        @foreach($user->testsHistory as $entry)
            <tr>
                <td>{{ $entry->created_at }}</td>
                <td>{{ $entry->{'title_' . App::getLocale()} }}</td>
                <td>{{ $entry->pivot->score }}</td>
                <td>{{ $entry->pivot->status }}</td>
            </tr>
        @endforeach
    </table>
</div>
@endsection
