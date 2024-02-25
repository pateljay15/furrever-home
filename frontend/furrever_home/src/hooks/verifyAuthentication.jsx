import React from 'react'

const verifyAuthentication = () => {

    const user = localStorage.getItem('token')
    const userRole = localStorage.getItem('role')
    console.log(userRole)

    if(user){
        return {user,userRole}
    }else{
        return false
    }
}

export default verifyAuthentication