var ViewModel = function () {

    var self = this;
    self.ProductoId = ko.observable();
    self.Nombre = ko.observable();
    self.Precio = ko.observable();
    self.Cantidad = ko.observable();

    self.productoList = ko.observableArray([]);

    var ProductoUri = '/api/producto';



    function ajaxFunction(uri, method, data) {

        //self.errorMessage('');  

        return $.ajax({

            type: method,
            url: uri,
            dataType: 'json',
            contentType: 'application/json',
            data: data ? JSON.stringify(data):null  
  
        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert('Error  ' + errorThrown);
        });
    }


    // Clear Fields  
    self.clearFields = function clearFields() {
        self.Nombre('');
        self.Precio('');
        self.Cantidad('');
    }

    //Agregar nuevo Producto  
    self.addNewProducto = function addNewProducto(nuevoProducto) {

        var objetoProducto = {
            ProductoId: self.ProductoId(),
            Nombre: self.Nombre(),
            Precio: self.Precio(),
            Cantidad: self.Cantidad()
        };
        ajaxFunction(ProductoUri, 'POST', objetoProducto).done(function () {

            self.clearFields();
            alert('producto Added Successfully !');
            getproductoList()
        });
    }
    //Get producto List  
    function getproductoList() {
        ajaxFunction(ProductoUri, 'GET').done(function (data) {
            self.productoList(data);
        });

    }

    //Get Detail producto  
    self.detailproducto = function (selectedProduct) {

        self.ProductoId(selectedProduct.ProductoId);
        self.Nombre(selectedProduct.Nombre);
        self.Precio(selectedProduct.Precio);
        self.Cantidad(selectedProduct.Cantidad);

        $('#Save').hide();
        $('#Clear').hide();

        $('#Update').show();
        $('#Cancel').show();

    };

    self.cancel = function () {

        self.clearFields();

        $('#Save').show();
        $('#Clear').show();

        $('#Update').hide();
        $('#Cancel').hide();
    }

    //Update producto  
    self.updateproducto = function () {

        var objetoProducto = {
            CustID: self.CustID(),
            FirstName: self.FirstName(),
            LastName: self.LastName(),
            Email: self.Email(),
            Country: self.Country()
        };

        ajaxFunction(ProductoUri + self.CustID(), 'PUT', objetoProducto).done(function () {
            alert('producto Updated Successfully !');
            getproductoList();
            self.cancel();
        });
    }

    //Delete producto  
    self.deleteproducto = function (producto) {

        ajaxFunction(ProductoUri + producto.CustID, 'DELETE').done(function () {

            alert('producto Deleted Successfully');
            getproductoList();
        })

    }

    getproductoList();

};

ko.applyBindings(new ViewModel());  