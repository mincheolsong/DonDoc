import { Route, Routes } from "react-router-dom";

// home tap & user
import HomePage from './pages/webmain/Home/Home'
import AccountInfoPage from "./pages/webmain/AccountInfo";
import SigninPage from './pages/webmain/Signin/Signin'
import SignupTempPage from "./pages/webmain/SignupTemp";
import SendMoneyFirstPage from "./pages/webmain/SendMoneyFirst";
import SendMoneySecondPage from "./pages/webmain/SendMoneySecond";
import SuccessPage from "./pages/webmain/SuccessPage";
import ErrorPage from "./pages/webmain/ErrorPage";
import SignUpFirstPage from "./pages/webmain/SignUpFirst";
import SignUpSecondPage from "./pages/webmain/SignUpSecond";
import CallAccountPage from "./pages/webmain/CallAccount";
import SendMoneyPasswordPage from "./pages/webmain/SendMoneyPassword";

// search
import SearchPage from './pages/search/Search/Search' 


// dondoc tap
import MoimHomePage from './pages/moim/MoimHome/MoimHome'
import CreateMoimPage from './pages/moim/CreateMoim/CreateMoim'
import MoimLinkAccountPage from './pages/moim/MoimLinkAccount/MoimLinkAccount'
import MoimSelectAccountPage from './pages/moim/MoimSelectAccount/MoimSelectAccount'
import CreateResultPage from './pages/moim/CreateResult/CreateResult'
import MoimJoinPage from './pages/moim/MoimJoin/MoimJoin'
import MoimPasswordPage from './pages/moim/CreatePassword/CreatePassword'
import MoimRePasswordPage from './pages/moim/CreatePasswordRe/CreatePasswordRe'
import MoimInfoPage from './pages/moim/MoimInfo/MoimInfo'
import AccountPassword from "./pages/moimdetail/DetailFirst/ChargeMoimAccount/AccountPassword/AccountPassword";


// mission tap
import MissionPage from './pages/mission/Mission/Mission'


// profile tap
import MypagePage from './pages/mypage/Mypage/Mypage' 
import SettingPage from './pages/mypage/Setting/Setting' 
import ChangePasswordPage from './pages/mypage/ChangePassword/ChangePassword' 
import AccountListPage from './pages/mypage/AccountList/AccountList' 
import ChangeCharacterPage from './pages/mypage/ChangeCharacter/ChangeCharacter' 
import DiffProfilePage from './pages/mypage/DiffProfile/DiffProfile' 
import FriendListPage from './pages/mypage/FriendList/FriendList'


//moim detail
import DetailMain from './pages/moimdetail/DetailMain/DetailMain'
// import DetailFirst from './pages/moimdetail/DetailFirst/DetailFirst'
// import DetailSecond from './pages/moimdetail/DetailSecond/DetailSecond'
// import DetailThird from './pages/moimdetail/DetailThird/DetailThird'

//Notification
import Noti_List from "./pages/notification/noti_list";
import Friend_Re from "./pages/notification/Request/FriendRequest/Friend_Re";

function Router() {

    return (
        <Routes>
            {/* user */}
            <Route path="/signin" element={<SigninPage/>}/>
            <Route path="/signupfirst" element={<SignUpFirstPage/>}/>
            <Route path="/signupsecond" element={<SignUpSecondPage/>}/>
            <Route path="/signupTemp" element={<SignupTempPage/>}/>
            
            {/* home tap */}
            <Route path="/" element={<HomePage/>}/>
            <Route path="/accountInfo/:accountid" element={<AccountInfoPage/>}/>
            <Route path="/sendmoneyfirst/:accountid" element={<SendMoneyFirstPage/>}/>
            <Route path="/sendmoneysecond/:accountid" element={<SendMoneySecondPage/>}/>
            <Route path="/callaccount" element={<CallAccountPage/>}/>
            <Route path="/error" element={<ErrorPage/>}/>
            <Route path="/success" element={<SuccessPage/>}/>
            <Route path="/sendmoneypassword" element={<SendMoneyPasswordPage/>}/>


            {/* search */}
            <Route path="/search" element={<SearchPage/>}/>


            {/* dondoc tap */}
            <Route path="/moimhome" element={<MoimHomePage/>}/>
            <Route path="/createmoim" element={<CreateMoimPage/>}/>
            <Route path="/moimlink" element={<MoimLinkAccountPage/>}/>
            <Route path="/moimselect" element={<MoimSelectAccountPage/>}/>
            <Route path="/createresult" element={<CreateResultPage/>}/>
            <Route path="/moimjoin" element={<MoimJoinPage/>}/>
            <Route path="/moimpassword" element={<MoimPasswordPage/>}/>
            <Route path="/moimrepassword" element={<MoimRePasswordPage/>}/>
            <Route path="/moiminfo" element={<MoimInfoPage/>}/>
            <Route path="/accountpassword" element={<AccountPassword/>}/>


            {/* mission tap */}
            <Route path="/mission/:userId" element={<MissionPage/>}/>


            {/* profile tap */}
            <Route path="/accountlist" element={<AccountListPage/>}/>
            <Route path="/changepassword" element={<ChangePasswordPage/>}/>
            <Route path="/changecharacter" element={<ChangeCharacterPage/>}/>
            <Route path="/diffprofile/:userId" element={<DiffProfilePage/>}/>
            <Route path='/mypage/:userId' element={<MypagePage/>}/>
            <Route path="/setting" element={<SettingPage/>}/>
            <Route path="/friendlist" element={<FriendListPage/>}/>


            {/* moim detail */}
            <Route path="/detailmain/:moimId" element={<DetailMain/>}/>
            {/* <Route path="/detailfirst/:moimId" element={<DetailFirst/>}/>
            <Route path="/detailsecond/:moimId" element={<DetailSecond/>}/>
            <Route path="/detailthird/:moimId" element={<DetailThird/>}/> */}

            {/* Notification */}
            <Route path='/notification/' element={<Noti_List />}></Route>
            <Route path='/notification/FriendRequests' element={<Friend_Re />}></Route>

            
        </Routes>
    )
}

export default Router