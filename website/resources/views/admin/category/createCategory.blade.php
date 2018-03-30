@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">Create Category</div>

                <div class="panel-body">
                    <form method="POST" action="/admin/categories/store">

                        {{ csrf_field() }}

                        <span class="language-marker">English:</span>
                        <div class="form-group">
                            <input name="title_en" class="form-control about-title" placeholder="English title" value="{{ old('title_en') }}"></input>
                        </div>

                        <span class="language-marker">Georgian:</span>
                        <div class="form-group">
                            <input name="title_ge" class="form-control about-title" placeholder="Georgian title" value="{{ old('title_ge') }}"></input>
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary pull-right">Save</button>
                        </div>

                    </form>

                    @include('admin/errors')
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
