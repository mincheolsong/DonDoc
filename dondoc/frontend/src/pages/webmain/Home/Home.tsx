import styles from "./Home.module.css";
import Nav from "../../Nav";
import Header from "../Header";
import peter from "../../../assets/image/peter.svg"
import { NavLink } from "react-router-dom";
import  {useNavigate}  from "react-router-dom";



function Home() {
 
  
  return (
    <div className={styles.container}>
      {/* 헤더, 유저박스 */}
      <Header/>
      <div style={{display:"flex" , flexDirection:"column",  alignItems:"center", marginTop:"1.5rem"}}>
      <UserBox userCharacter={peter} username="peter" rightBtn="계좌개설하기"/>
      {/* 헤더, 유저박스 */}


      {/* 나의계좌 */}
      <div style={{display:"flex",flexDirection:"row",justifyContent:"space-between",alignItems:"end",width:"98%",marginTop:"1rem"}}>
      <p style={{fontSize:"2.2rem",fontWeight:"bold", marginBottom:"1rem"}}>나의 계좌</p>
      <button className={styles.accountBtn}>계좌불러오기</button>
      </div>
      {/* 나의계좌 */}


      <div className={styles.midContainer}>
        <div style={{display:"flex",flexDirection:"row"}}>
          은행 사진

          <div>
          계좌이름
          <br /> 
          잔액
        </div>

        </div>
      
        
        <div>
          송금버튼
        </div>
      </div>
    



      <Nav/>
      </div>
    </div>
  );
}

export default Home;


export function UserBox(props){
  const navigate = useNavigate();
  return(
    <div className={styles.topContainer}>
      <div style={{display:"flex",width:"60%"}}>
      <img src={props.userCharacter} style={{width:"35%"}} />
    <div style={{marginLeft:"1rem",textAlign:"center"}}>
      <p style={{fontSize:"1.2rem",fontWeight:"bold"}}>{props.username} 의 DonDoc</p>
      <button className={styles.myProfileBtn} onClick={()=>{
        navigate("/mypage")
      }}> 나의프로필가기</button>
    </div>
      </div>
    
    <div>
      <button className={styles.myProfileBtn} style={{height:"5rem",fontSize:"1.2rem"}} > {props.rightBtn}</button>
    </div>
  </div>

  )

}



export function accountItem(){
  return(
    <div>
  
    </div>
  )
}
