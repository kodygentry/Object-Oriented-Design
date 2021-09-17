# How to use JavaFX 11 in Eclipse IDE?


#### You can also use the official [OpenJFX tutorial](https://openjfx.io/openjfx-docs/) to find out how to install and use JavaFX 11.


## Downloading JavaFX

Go to either the [JavaFX site](https://openjx.io) or directly to the [download page](https://gluonhq.com/products/javafx/) and download JavaFX 11 for your OS.

Put the contents of this zip file somewhere on your system, for example in `C:\Program Files\C:\Program Files\OpenJFX`. If done correctly, this folder now contains a folder with a name along the lines of `javafx-sdk-11`.

**Warning, Look out for the version number behind the folder**

The structure inside the `javafx-sdk-11` will look something like this:

```
/bin/
	(on Windows this will contain some .dll files)
/legal/
	(contains licenses)
/lib/
	(this contains some .jar files)
```

## Using JavaFX in Eclipse

To use JavaFX 11 with Eclipse you will need to do 2 things, add the module path to the VM arguments, and add the libraries to Eclipse.

### Adding the libraries to Eclipse

To add the libraries, edit the build path, and add the JavaFX .jar files to the module path, do this by clicking on the `Add External JARS` button, and selecting all the JavaFX modules. The result will look something like this.

![Imgur](https://i.imgur.com/5rhYXi7.png)


After doing this, the JavaFX imports should now be fixed.

To also be able to compile and run the code, you should add the modules to the VM arguments too.

To do so, edit the `Run Configurations` and go to the `Arguments` tab. In the `VM arguments` field, enter the following lines, with the path changed to your actual OpenJFX install location.

```
--module-path="C:\Program Files\OpenJFX\javafx-sdk-11\lib" --add-modules=javafx.controls,javafx.fxml
```

If you need other JavaFX modules than `controls` and `fxml`, then these should also be added to the same `--add-modules` line, separated by a comma.

After this, your configuration will look like this.

![Imgur](https://i.imgur.com/S55P8Cx.png)

