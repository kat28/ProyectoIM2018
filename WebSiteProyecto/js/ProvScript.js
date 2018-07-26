            
$(window).on("load", function () {

    $('#btnSearch').on('click', function () {
        GetProductoById($('#txtIdProveedor').val());
    })

    $('#btnDelete').on('click', function () {
        DeleteProductoById($('#txtIdProveedor').val());
    })

    $('#btnUpdate').on('click', function () {
        var proveedor = new Object();
        proveedor.IdProveedor = $('#txtIdProveedor').val();
        proveedor.Nombre = $('#txtNombre').val();
        proveedor.Direccion = $('#txtDireccion').val();
        proveedor.Telefono = $('#txtTelefono').val();
        UpdateProducto(proveedor.IdProveedor, JSON.stringify(proveedor));
    })

    $('#btnCreate').on('click', function () {
        var proveedor = new Object();
        proveedor.IdProveedor = $('#txtIdProveedor').val();
        proveedor.Nombre = $('#txtNombre').val();
        proveedor.Direccion = $('#txtDireccion').val();
        proveedor.Telefono = $('#txtTelefono').val();
        CreateProducto(proveedor.IdProveedor, JSON.stringify(proveedor));
    })

   GetAll();
})
            //Obtener todos los proveedores
            function GetAll() {
                var item = "";
                $('#tblList tbody').html('');
                $.getJSON('http://apiproyecto2018.azurewebsites.net/api/proveedor', function (data) {
                    $.each(data, function (key, value) {
                        item += "<tr><td>" + value.IdProveedor+ "</td><td>" + value.Nombre + "</td><td>" + value.Direccion + "</td><td>" + value.Telefono + "</td></tr>";
                    });
                    $('#tblList tbody').append(item);
                });
            };


            //Get Proveedor by id
            function GetProductoById(IdProveedor) {
                var url = 'http://apiproyecto2018.azurewebsites.net/api/proveedor/' + IdProveedor;
                $.getJSON(url)
                .done(function (data) {
                    $('#txtIdProveedor').val(data.IdProveedor);
                    $('#txtNombre').val(data.Nombre);
                    $('#txtDireccion').val(data.Direccion);
                    $('#txtTelefono').val(data.Telefono);
                })
                .fail(function (erro) {
                    ClearForm();
                    alert('El producto con el id: ' + IdProveedor + ' no ha sido encontrado');
                });
            };

            //Delete proveedor by id
            function DeleteProductoById(IdProveedor) {
                var url = 'http://apiproyecto2018.azurewebsites.net/api/proveedor/' + IdProveedor;
                $.ajax({
                    url: url,
                    type: 'DELETE',
                    contentType: "application/json",
                    statusCode: {
                        200: function () {
                            GetAll();
                            ClearForm();
                            alert('El producto con el id: ' + IdProveedor + ' ha sido eliminado');
                        },
                        404: function () {
                            alert('El producto con el id: ' + IdProveedor + ' no ha sido encontrado');
                        }
                    }
                });
            }

            //Update proveedor
            function UpdateProducto(IdProveedor, proveedor) {
                var url = 'http://apiproyecto2018.azurewebsites.net/api/proveedor/' + IdProveedor;
                $.ajax({
                    url: url,
                    type: 'PUT',
                    data: proveedor,
                    contentType: "application/json",
                    success: function (IdProveedor, textStatus, xhr) {
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

            //Create un proveedor
            function CreateProducto(IdProveedor, proveedor) {
                var url = 'http://apiproyecto2018.azurewebsites.net/api/proveedor/';
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: proveedor,
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
                            alert('El proveedor con el id: ' +IdProveedor+ ' ya existe');
                        }
                    }
                });
            }

            //Clear form
            function ClearForm() {
                $('#txtIdProveedor').val('');
                $('#txtNombre').val('');
                $('#txtDireccion').val('');
                $('#txtTelefono').val('');
            }