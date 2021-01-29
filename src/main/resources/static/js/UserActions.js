function addUser(event){
    let roles = [];
    let wTile = event.target.parentNode.querySelectorAll('input');
    if (wTile[4].checked) {
        role = {"authority":"user","id":wTile[4].value,"name":"user"}
        roles.push(role)
    }
    if (wTile[5].checked) {
        role = {"authority":"ADMIN","id":wTile[5].value,"name":"ADMIN"}
        roles.push(role)
    }
    console.log("ro"+roles.length)
    let user = {"email":wTile[2].value,"last_name":wTile[1].value,"password":wTile[3].value,"username":wTile[0].value,"roles" : roles};
    console.log(user);
    wTile[0].value="";
    wTile[1].value="";
    wTile[2].value="";
    wTile[3].value="";
    wTile[4].checked=false;
    wTile[5].checked=false;
    createUser(user);
}
function editUser(event) {
    let roles = [];
    let role
    let wTile = event.target.parentNode.parentNode.querySelectorAll('input');
    if (wTile[5].checked) {
        role = {"authority":"user","id":wTile[5].value,"name":"user"}
        roles.push(role)
    }
    if (wTile[6].checked) {
        role = {"authority":"ADMIN","id":wTile[6].value,"name":"ADMIN"}
        roles.push(role)
    }
    console.log("ro"+roles.length)

    let user = {"email":wTile[3].value,"id":wTile[0].value,"last_name":wTile[2].value,"password":"","username":wTile[1].value,"roles" : roles};
    console.log(user);
    updateUser(user,wTile[0].value)
    myFunction()
    $('.modal-backdrop').remove();
}
async function updateUser(user,id){
    let response = await fetch('/admin/'+id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    });
    console.log(response)
}
async function createUser(user){
    console.log("create user"+user)
    let response = await fetch('/admin/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    });
    console.log(response)
}
async function deleteUser(deleteUser){
    let response = await fetch('/admin/'+deleteUser, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
    });
    console.log(response)
    myFunction()
}
async function myFunction() {
    let response = await fetch("/admin/");
    let roles = await fetch("/admin/roles/");
    if (response.ok && roles.ok) {
        let json = await response.json();
        let jsonRoles = await roles.json();
        document.getElementById("tablecontent").innerHTML = "";
        document.getElementById("tablecontent").insertAdjacentHTML('afterbegin',"<thead>\n" +
            "                                    <tr>\n" +
            "                                        <th scope=\"col\">ID</th>\n" +
            "                                        <th scope=\"col\">First Name</th>\n" +
            "                                        <th scope=\"col\">Last Name</th>\n" +
            "                                        <th scope=\"col\">Email</th>\n" +
            "                                        <th scope=\"col\">Roles</th>\n" +
            "                                        <th scope=\"col\">Edit</th>\n" +
            "                                        <th scope=\"col\">Delete</th>\n" +
            "                                    </tr>\n" +
            "                                    </thead>")
        var content = "";
        var rolesStr =""
        jsonRoles.forEach(role =>{
            rolesStr=rolesStr.concat("<input name=\"roles\" class=\"m-2\" type=\"checkbox\"  value='"+role.id+"'/>"+role.name);
        })
        document.getElementById('userroles').innerHTML = rolesStr;

        json.forEach(element =>{
            console.log(element);
            var userRoles=""
            let roles = element.roles;
            roles.forEach(role=>{
                userRoles=userRoles.concat("<span><span>"+role.name+"</span></span>");
            });
            content=content.concat("<tr >\n" +
                "                                        <td>"+element.id+"</td>\n" +
                "                                        <td>"+element.username+"</td>\n" +
                "                                        <td>"+element.last_name+"</td>\n" +
                "                                        <td>"+element.email+"</td>\n" +
                "                                        <td >"+userRoles+"</td>\n" +
                "                                        <td ><a data-val='"+element.id+"' class=\"btn btn-info edit\" data-target='#exampleModal"+element.id+"'  data-toggle=\"modal\">Edit</a></td>\n" +
                "                                        <td ><a onclick=\"deleteUser("+element.id+")\" class=\"btn btn-danger\">Delete</a></td>\n" +
                "                                        <div class=\"modal fade\" id='exampleModal"+element.id+"' tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n" +
                "                                            <div class=\"modal-dialog\" role=\"document\">\n" +
                "                                                <div class=\"modal-content\">\n" +
                "                                                    <div align=\"center\">\n" +
                "                                                        <form action=\"#\"  style=\"display: table-cell;text-align: center;vertical-align: middle;\">\n" +
                "                                                            <input type=\"hidden\" class=\"form-control\" name=\"id\"  placeholder=\"firstName\" value='"+element.id+"'>\n" +
                "                                                            <div class=\"form-group\">\n" +
                "                                                                <label >First Name</label>\n" +
                "                                                                <input type=\"text\" class=\"form-control\" name=\"username\"  placeholder=\"firstName\" value='"+element.username+"'>\n" +
                "                                                            </div>\n" +
                "                                                            <div class=\"form-group\">\n" +
                "                                                                <label >Last Name</label>\n" +
                "                                                                <input type=\"text\" class=\"form-control\" name=\"last_name\" placeholder=\"lastName\" value='"+element.last_name+"'>\n" +
                "                                                            </div>\n" +
                "                                                            <div class=\"form-group\">\n" +
                "                                                                <label >Email</label>\n" +
                "                                                                <input type=\"text\" class=\"form-control\" name=\"email\" placeholder=\"Email\" value='"+element.email+"'>\n" +
                "                                                            </div>\n" +
                "                                                            <div class=\"form-group\">\n" +
                "                                                                <label >Password</label>\n" +
                "                                                                <input type=\"password\" class=\"form-control\" name=\"password\" placeholder=\"Password\" >\n" +
                "                                                            </div>\n" +
                "                                                            <div class=\"form-group\">\n" +
                "                                                                <label >Role</label>\n" +
                "                                                                <div class=\"col-sm-8 text-left\">\n" +
                "                                                                        <div>"+rolesStr+"</div>"+
                "                                                                </div>\n" +
                "                                                            </div>\n" +
                "                                                            <div class=\"modal-footer\">\n" +
                "                                                                <button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Close</button>\n" +
                "                                                                <button onclick=\"editUser(event)\" type=\"submit\" class=\"btn btn-primary\">Edit User</button>\n" +
                "                                                            </div>\n" +
                "                                                        </form>\n" +
                "                                                    </div>\n" +
                "                                                </div>\n" +
                "                                            </div>\n" +
                "                                        </div>\n" +
                "                                    </tr>")});
        document.getElementById("tablecontent").insertAdjacentHTML('beforeend',content)
    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}