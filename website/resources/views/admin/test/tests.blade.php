@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Tests
                    <a class="pull-right" href="{{ url('/admin/tests/create/') }}">Add New</a>
                </div>

                <div class="panel-body">
                    @foreach ($tests as $test)
                        <div class="about-container" style="margin-bottom: 20px">
                            <span class="about-title">{{ $test->title_en }}</span>

                            <a class="delete pull-right" href="{{ url('/') . '/admin/tests/delete/' . $test->id }}">Delete</a>
                            <a class="update pull-right" style="margin-right: 10px" href="{{ url('/') . '/admin/tests/edit/' . $test->id }}">Edit</a>
                        </div>
                    @endforeach
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
