import { useState } from "react";
import Nav from "../../Nav/Nav";
import { useSelector } from "react-redux";
import { UserType } from "../../../store/slice/userSlice";
import styles from "./Search.module.css";
import { BASE_URL } from "../../../constants";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { dividerClasses } from "@mui/material";

type User = {
  userId: number,
  phoneNumber: string,
  imageNumber: number,
  bankName: string,
  bankCode: number,
  accountNumber: string,
  msg: string,
  nickName: string
}

function Search() {
  const [Result, setUserList] = useState<User[]>([])
  const [PhoneNumber, setPhoneNumber] = useState<string>('')

  const navigate = useNavigate()
  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const GoProfile = (id:number) => {
    navigate(`profile/${id}`)
  }

  const InputNumber = (e:React.ChangeEvent<HTMLInputElement>) => {
    setPhoneNumber(e.target.value)
  }

  const SearchUser = async() => {
    try {
      const response = await axios.get(`${BASE_URL}/api/user/find_user/${PhoneNumber}`,{
        headers: {
        'Content-Type': 'application/json', 
        'Authorization': 'Bearer ' + token
      }}
      );
      setUserList(response.data.response)
      console.log(response.data.response)
    } catch(error) {
      console.log('error:', error)
    }
  }


  return (
    <>
    <div className={styles.Background}>
      <button onClick={SearchUser}>검색</button>
      <input className={styles.SearchBar} placeholder="추가하고 싶은 친구의 전화번호를 입력해 주세요."
      onChange={InputNumber}/>
      {Result.length ?     <div className={styles.topContainer}>
      <div style={{display:"flex",width:"60%"}}>
      <img src={Result.imageNumber} style={{width:"35%"}} />
    <div style={{marginLeft:"1rem",textAlign:"center"}}>
      <p style={{fontSize:"1.2rem",fontWeight:"bold"}}>{Result.nickname}</p>
    </div>
      </div>
    <div>
      <button className={styles.myProfileBtn} style={{height:"5rem",fontSize:"1.2rem"}} 
      onClick = {() => GoProfile(Result.userId)}>프로필 가기</button>
    </div>
  </div> : <div className={styles.ResultContainer}>검색 내용이 없습니다.</div>}
    
    </div>
    <Nav />
    </>
  );
}

export default Search;