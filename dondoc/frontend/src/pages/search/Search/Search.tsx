import { useState } from "react";
import Nav from "../../Nav/Nav";
import { useSelector } from "react-redux";
import { UserType } from "../../../store/slice/userSlice";
import styles from "./Search.module.css";
import { BASE_URL } from "../../../constants";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {BiSearch} from "react-icons/bi"
import BackLogoHeader from "../../toolBox/BackLogoHeader";


type Results = {
  userId: number,
  phoneNumber: string,
  imageNumber: number,
  bankName: string,
  bankCode: number,
  accountNumber: string,
  msg: string,
  nickName: string
}

const initialSearchResult: Results = {
  userId: 0,
  phoneNumber: "",
  imageNumber: 0,
  bankName: "",
  bankCode: 0,
  accountNumber: "",
  msg: "",
  nickName: ""
};

function Search() {
  const [Result, setResult] = useState<Results>(initialSearchResult)
  const [PhoneNumber, setPhoneNumber] = useState<string>('')

  const navigate = useNavigate()
  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const GoProfile = (id:number) => {
    navigate(`/diffprofile/${id}`)
  }

  const InputNumber = (e:React.ChangeEvent<HTMLInputElement>) => {
    setPhoneNumber(e.target.value)
  }

  const SearchUser = async() => {
    try {
      const res = await axios.get(`${BASE_URL}/api/user/find_user/${PhoneNumber}`, {
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': 'Bearer ' + token
        }
      });
      if (res.data.response) {
        // console.log(res.data.response)
        setResult(res.data.response)
      } else {
        // console.log(res.data)
        alert(res.data.error.message)

        setResult(initialSearchResult);
      }
    }catch(err) {
      // console.log(err)
    }
  }


  return (
    <>
    <div className={styles.Background}>

    <div style={{marginBottom:"20%"}}>
      <input onChange={InputNumber} className={styles.searchBox} placeholder="전화번호를 입력해주세요" type="text" name="" id="" />
      <BiSearch className={styles.searchIcon} onClick={SearchUser}/>
      </div>
      {Result.userId ? (<div className={styles.topContainer}>
      <div style={{display:"flex",width:"60%"}}>
      <img src={`/src/assets/characterImg/${Result.imageNumber}.png`} style={{width:"35%"}} />
    <div style={{marginLeft:"1rem",textAlign:"center"}}>
      <p style={{fontSize:"1.5rem",fontWeight:"bold",fontFamily:"NT"}}>{Result.nickName}</p>
    </div>
      </div>
    <div>
      <button className={styles.myProfileBtn} style={{height:"5rem",fontSize:"1.2rem"}} 
      onClick = {() => GoProfile(Result.userId)}>프로필 가기</button>
    </div>
  </div>) : <div className={styles.ResultContainer}>검색 내용이 없습니다.</div>}
    
    </div>
    <Nav />
    </>
  );
}

export default Search;