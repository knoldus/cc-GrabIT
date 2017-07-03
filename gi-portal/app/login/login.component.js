"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var index_1 = require("../_services/index");
var LoginComponent = (function () {
    function LoginComponent(route, router, authenticationService, alertService, elementRef) {
        this.route = route;
        this.router = router;
        this.authenticationService = authenticationService;
        this.alertService = alertService;
        this.elementRef = elementRef;
        this.model = {};
        this.loading = false;
    }
    LoginComponent.prototype.ngOnInit = function () {
        // reset login status
        this.authenticationService.logout();
        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
        //sidebar menu toggle
        jQuery(this.elementRef.nativeElement).find('#menu-toggle').on('click', function (e) {
            e.preventDefault();
            jQuery("#wrapper").toggleClass("toggled");
        });
    };
    LoginComponent.prototype.login = function () {
        var _this = this;
        this.loading = true;
        this.authenticationService.login(this.model.email, this.model.password)
            .subscribe(function (data) {
            // this.router.navigate([this.returnUrl]);
            var userRole = data.role;
            if (userRole === 'admin') {
                _this.router.navigate(['/home/manage/dashboard']);
            }
            else {
                _this.router.navigate(['/home/user/dashboard']);
            }
        }, function (error) {
            if (error.status === 0) {
                swal('Error Occurred', "Uh!! Some issue, Please try again or check your connections.", 'error');
            }
            else {
                swal('Error Occurred', error._body, 'error');
            }
            _this.loading = false;
        });
    };
    return LoginComponent;
}());
LoginComponent = __decorate([
    core_1.Component({
        moduleId: module.id,
        templateUrl: 'login.component.html',
        styleUrls: ['login.component.css']
    }),
    __metadata("design:paramtypes", [router_1.ActivatedRoute,
        router_1.Router,
        index_1.AuthenticationService,
        index_1.AlertService,
        core_1.ElementRef])
], LoginComponent);
exports.LoginComponent = LoginComponent;
//# sourceMappingURL=login.component.js.map