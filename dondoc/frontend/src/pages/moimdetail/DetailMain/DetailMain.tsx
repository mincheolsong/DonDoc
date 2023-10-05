import styles from "./DetailMain.module.css";
import DetailFirst from "../DetailFirst/DetailFirst"
import DetailSecond from "../DetailSecond/DetailSecond"
import DetailThird from "../DetailThird/DetailThird"
import { useLocation, useParams } from "react-router-dom";
import axios from "axios";
import { BASE_URL } from "../../../constants";
import { useSelector } from "react-redux";
import { UserType } from "../../../store/slice/userSlice";
import { useEffect, useState } from 'react'
import BackLogoHeader from "../../toolBox/BackLogoHeader/BackLogoHeader";


function DetailMain() {

  const { moimId } = useParams();
  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const { state } = useLocation()
  const userType = state.userType
  const accountId = state.accountId

  const [moimName, setMoimName] = useState<string>('')


  useEffect(() => {
    const fetchData = async () => {
      try {
        // moim/detail/${moimId}
        const res = await axios.get(`${BASE_URL}/api/moim/detail/${moimId}`, {
          headers: {
            'Content-Type': 'application/json', 
            'Authorization': 'Bearer ' + token
          }
        });
        // console.log('모임 멤버:', res.data.response)
        setMoimName(res.data.response.moimName)
      }
      catch(err) {
        console.log(err)
      }
    }
    fetchData();
  }, []);

  return (
    <div className={styles.container}>

      <BackLogoHeader name={moimName} fontSize="2rem" left="5rem" top="0.8rem"/>

      <DetailFirst userType={userType} accountId={accountId} moimId={moimId}/>
      <DetailSecond moimId={moimId} memberType={userType}/>
      <DetailThird />
    </div>
  );
}

export default DetailMain;
