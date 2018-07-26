            
$(window).on("load", function () {

    $('#btnSearchProducto').on('click', function () {
        GetProductoById($('#txtIdProducto').val());
    })

    $('#btnEliminarProducto').on('click', function () {
        DeleteProductoById($('#txtIdProducto').val());
    })

    $('#btnModificarProducto').on('click', function () {
        var producto = new Object();
        producto.IdProducto = $('#txtIdProducto').val();
        producto.Nombre = $('#txtNombreProducto').val();
        producto.Precio = $('#txtPrecio').val();
        producto.Cantidad = $('#txtCantidad').val();
        UpdateProducto(producto.IdProducto, JSON.stringify(producto));
    })

    $('#btnNuevoProducto').on('click', function () {
        var producto = new Object();
        producto.IdProducto = $('#txtIdProducto').val();
        producto.Nombre = $('#txtNombreProducto').val();
        producto.Precio = $('#txtPrecio').val();
        producto.Cantidad = $('#txtCantidad').val();
        CreateProducto(producto.IdProducto, JSON.stringify(producto));
    })

    GetAll();
})

        //Get lista productos
        function GetAll() {
            var item = "";
            $('#tblList tbody').html('');
            $.getJSON('http://apiproyecto2018.azurewebsites.net/api/producto', function (data) {
                $.each(data, function (key, value) {
                    item += "<tr><td>" + value.IdProducto+ "</td><td>" + value.Nombre + "</td><td>" + value.Precio + "</td><td>" + value.Cantidad + "</td></tr>";
                });
                $('#tblList tbody').append(item);
            });
        };



        //Get Producto by id
        function GetProductoById(IdProducto) {
            var url = 'http://apiproyecto2018.azurewebsites.net/api/producto/' + IdProducto;
            $.getJSON(url)
            .done(function (data) {
                $('#txtIdProducto').val(data.IdProducto);
                $('#txtNombreProducto').val(data.Nombre);
                $('#txtPrecio').val(data.Precio);
                $('#txtCantidad').val(data.Cantidad);
            })
            .fail(function (erro) {
                ClearForm();
            });
        };

        //Delete producto by id
        function DeleteProductoById(IdProducto) {
            var url = 'http://apiproyecto2018.azurewebsites.net/api/producto/' + IdProducto;
            $.ajax({
                url: url,
                type: 'DELETE',
                contentType: "application/json",
                statusCode: {
                    200: function () {
                        GetAll();
                        ClearForm();
                        alert('El producto con el id: ' + IdProducto + ' ha sido eliminado');
                    },
                    404: function () {
                        alert('El producto con el id: ' + IdProducto + ' no ha sido encontrado');
                    }
                }
            });
        }

        //Update producto
        function UpdateProducto(IdProducto, Producto) {
            var url = 'http://apiproyecto2018.azurewebsites.net/api/producto/' + IdProducto;
            $.ajax({
                url: url,
                type: 'PUT',
                data: Producto,
                contentType: "application/json",
                success: function (IdProducto, textStatus, xhr) {
                    GetAll();
                    ClearForm();
                    alert('El producto fue modificado correctamente');
                },

                error: function (xhr, textStatus, errorThrown) {
                    ClearForm;
                    alert('Error');
                }

            });
        }

            //Create producto
            function CreateProducto(IdProducto, Producto) {
                var url = 'http://apiproyecto2018.azurewebsites.net/api/producto';
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: Producto,
                    contentType: "application/json",
                    statusCode: {
                        201: function () {
                            GetAll();
                            ClearForm();
                            alert('El producto ha sido agregado');
                        },

                        400: function () {
                            ClearForm();
                            alert('Error');
                        },

                        409: function () {
                            ClearForm();
                            alert('El producto con el id: ' +IdProducto+ ' ya existe');
                        }
                    }
                });
            }

         //Limpiar Formulario
         function ClearForm() {
            $('#txtIdProducto').val('');
            $('#txtNombreProducto').val('');
            $('#txtPrecio').val('');
            $('#txtCantidad').val('');
        }