const options = {
    headers: {"content-type": "application/json"}
}
hideLoading()

let applicationList = []

function getApplications(applications) {
    showLoading()
    applications.forEach(application => {
        let applicationId = application.id
        let applicationVendor = application.vendor
        let applicationData = createApplicationData(applicationId, applicationVendor)
        applicationList.push(applicationData)
    });
}


function getOffersWithRequest() {
    delay(5000).then(() => {
        axios.post('/offers', createOfferRequest(), options)
            .then(function (response) {
                processOffers(response.data)
                if (applicationList.length > 0) {
                    getOffersWithRequest()
                } else {
                    hideLoading()
                }
            })
            .catch(function (error) {
                showErrors(error.response.data)
            });
    })
}

function sendApplication() {
    $("div.error").remove();
    let data = prepareFormData()
    axios.post('/application', data, options)
        .then(function (response) {
            hideForm()
            getApplications(response.data.applications)
            getOffersWithRequest()
        })
        .catch(function (error) {
            showForm()
            showErrors(error.response.data)
        });
}

function prepareFormData() {
    return {
        phone: $("#phone").val(),
        email: $("#email").val(),
        monthlyIncome: $("#monthlyIncome").val(),
        monthlyExpenses: $("#monthlyExpenses").val(),
        monthlyCreditLiabilities: $("#monthlyCreditLiabilities").val(),
        dependents: $("#dependents").val(),
        maritalStatus: $("#maritalStatus").val(),
        amount: $("#amount").val(),
        agreeToBeScored: $("#agreeToBeScored").is(":checked"),
        agreeToDataSharing: $("#agreeToDataSharing").is(":checked"),

    }
}

function createApplicationData(applicationId, vendor) {
    return {
        applicationId: applicationId,
        vendor: vendor
    }
}


function createOfferRequest() {
    return {
        applicationDataList: applicationList
    }
}

function showLoading() {
    $(".loading").show()
}

function hideLoading() {
    $(".loading").hide()
}

function hideForm() {
    $(".form-panel").hide()
}

function showForm() {
    $(".form-panel").show()
}

function delay(time) {
    return new Promise(resolve => setTimeout(resolve, time));
}

function processOffers(applications) {

    applications.forEach(application => {
        if (application.status === "PROCESSED") {
            if (application.offer === null) {
                removeFromApplicationList(application.id)
            } else {
                createBlock(application)
                removeFromApplicationList(application.id)
            }
        }
    })
}

function removeFromApplicationList(id) {
    applicationList = applicationList.filter(function (value, index, arr) {
        return value.applicationId !== id;
    });
}

function createBlock(application) {
    var offer = application.offer
    var vendor = application.vendor
    var block =
        '<div style="border-style: solid;">' +
        '<h3>' +
        application.vendor +
        '</h3> Monthly payment amount: ' +
        offer.monthlyPaymentAmount +
        '<br>' +
        'Total repayment amount: ' +
        offer.totalRepaymentAmount +
        '<br>' +
        'Number of payments: ' +
        offer.numberOfPayments +
        '<br>' +
        'Annual percentage rate: ' +
        offer.annualPercentageRate +
        '<br>' +
        'First repayment date: ' +
        offer.firstRepaymentDate +
        '</div>' +
        '<br>';

    $('body').append(block);
}

function showErrors(errors) {
    showErrorMessage(errors)
    const map = new Map(Object.entries(errors.fieldErrors));
    for (const key of map.keys()) {
        showOneError(key, map.get(key))
    }
}

function showOneError(fieldId, fieldErr) {
    val = '.' + fieldId + 'Err'
    $(val).append('<div class="error" style="color: red">' + fieldErr + '</div>')
}

function showErrorMessage(errors){
    if (errors.errorMeassage !== undefined) {
        alert(errors.errorMeassage)
    }
}
