<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;

class ImageController extends Controller
{
    /**
     * display image upload page
     *
     */
    public function uploadPage()
    {
        $files = array_slice(Storage::files('public/ckuploads'), 0, 8);
        for($i = 0; $i < count($files); $i++) {
            $files[$i] = str_replace("public","/storage",$files[$i]);
        }
        return view('admin/uploads/upload', compact('files'));
    }

    /**
     * uplaod image and display url
     */
    public function upload(Request $request)
    {
        $validatedData = $request->validate([
            'cover_photo' => 'required|mimes:jpeg,bmp,png,svg|max:2000'
        ]);

        $coverPath = '';
        if($request->hasFile('cover_photo') && $request->file('cover_photo')->isValid()) {
            $coverPath = $request->file('cover_photo')->storeAs('ckuploads', $request->file('cover_photo')->getClientOriginalName(), 'public');
            $coverPath = url('/') . '/storage/' . $coverPath;
            $files = Storage::files('public/ckuploads');
            for($i = 0; $i < count($files); $i++) {
                $files[$i] = str_replace("public","/storage",$files[$i]);
            }
            return redirect()->route('uploads', [
                'url' => $coverPath,
                'files' => $files
            ]);
        }
        $files = array_slice(Storage::files('public/ckuploads'), 0, 8);
        for($i = 0; $i < count($files); $i++) {
            $files[$i] = str_replace("public","/storage",$files[$i]);
        }
        return redirect()->route('uploads', [
            'url' => 'Error! Try again',
            'files' => $files
        ]);
    }

    /**
     * display image uploads in the given interval
     *
     */
    public function fetchUploads($page)
    {
        $files = array_slice(Storage::files('public/ckuploads'), $page*8, 8);
        for($i = 0; $i < count($files); $i++) {
            $files[$i] = str_replace("public","/storage",$files[$i]);
        }
        return view('renders.uploadsRender', compact('files'))->render();
    }

    /**
     * uplaod image and display url
     */
    public function deleteUpload($image)
    {

        $productImage = '/ckuploads/' . $image;
        Storage::delete('/public' . $productImage);

        $files = Storage::files('public/ckuploads');
        for($i = 0; $i < count($files); $i++) {
            $files[$i] = str_replace("public","/storage",$files[$i]);
        }
        return redirect()->route('uploads', [
            'files' => $files
        ]);
    }

    public function getImage($image)
    {
        return redirect('/storage/ckuploads/' . $image);
    }

    public function getEnImage($image)
    {
        return redirect('/storage/ckuploads/en/' . $image);
    }

    public function getGeImage($image)
    {
        return redirect('/storage/ckuploads/ge/' . $image);
    }
}
