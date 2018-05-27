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

    <table style="width:100%">
      <tr>
        <th>Date</th>
        <th>Name</th>
        <th>Answer</th>
        <th>Status</th>
      </tr>
      <tr>
        <td>Jill</td>
        <td>Smith</td>
        <td>50</td>
        <td>Correct</td>
      </tr>
    </table>
</div>
@endsection
