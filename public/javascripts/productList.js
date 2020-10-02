(function () {
    var fixDataMapIndexes = function() {
        $('[id*=job-data-map]').each(function(i, element){
            if (i % 2 === 0) {
                var itemNumber = i / 2;
                element.id = 'job-data-map_' + itemNumber + '_name';
                element.name = 'job-data-map[' + itemNumber + ']._name';
            }

            if (i % 2 !== 0) {
                var itemNumber = (i - 1) / 2;
                element.id = 'job-data-map_' + itemNumber + '_quantity';
                element.name = 'job-data-map[' + itemNumber + '].quantity';
            }
        })
    };

    $('.job-data-map').on('click', '.job-data-delete a', function () {
        $(this).parent().next().next().remove();
        $(this).parent().next().remove();
        $(this).parent().remove();

        fixDataMapIndexes();
    });

    $('.job-data-add').click(function () {
        var name = $(this).prev().prev().clone();
        var quantity = $(this).prev().clone();

        $(this).prev().prev().before($('<div class="job-data-delete text-right"><a href="#">delete</a></div>'));

        name.find('input').val('');
        quantity.find('input').val('');

        $(this).before(name);
        $(this).before(quantity);

        fixDataMapIndexes();
    });

    $('form').submit(function () {
        fixDataMapIndexes();
    });
})();