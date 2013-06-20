package secureapisampleapp

import grails.converters.JSON

class ApiController {
    def secureApiService

    def beforeInterceptor = {
        def isAuthorized = secureApiService.isAuthorizedRequest(request)
        if (isAuthorized == 200) {
            return true
        } else {
            render status: isAuthorized
            return false
        }
    }

    static users = [
            [id: "1",
                    firstName: "John Sr",
                    lastName: "Doe",
                    age: 51
            ],
            [id: "2",
                    firstName: "John",
                    lastName: "Doe",
                    age: 52
            ],
            [id: "3",
                    firstName: "John Jr",
                    lastName: "Doe",
                    age: 53
            ]
    ]


    def getUsers() {
        render text: users as JSON
    }

    def getUser() {
        def userId = params.id

        if (!userId || !users*.id.contains(userId)) {
            render status: 404
            return
        }

        render test: users.find() {it.id == userId} as JSON
    }
}
