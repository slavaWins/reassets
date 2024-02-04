<?php

    if (!isset($_GET['token'])) die(json_encode(['success' => false, 'message' => "no token"]));
    if ($_GET['token'] != "xxx") die(json_encode(['success' => false, 'message' => "bad token"]));


    $foolder = "resourcepacks/";
    if (!file_exists($foolder)) {
        mkdir($foolder);
    }

    $filename = md5(time().'rand'.rand(1, 9999999)).md5(time() - 1).".zip";
    $fileData = file_get_contents('php://input');
    $fhandle = fopen($foolder.$filename, 'wb');
    fwrite($fhandle, $fileData);
    fclose($fhandle);
    //  echo("Done uploading");

    $protocol = isset($_SERVER['HTTPS']) && $_SERVER['HTTPS'] === 'on' ? 'https' : 'http';
    $domain = $protocol.'://'.$_SERVER['HTTP_HOST'];

    die(json_encode(['success' => true, 'url' => $domain.'/'.$foolder.$filename]));


