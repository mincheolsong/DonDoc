import styles from "./AccountInfo.module.css";
import { BackLogo } from "../Signup/Signup";




function AccountInfo() {
  return (
  <div>
    
    <BackLogo name="계좌이름" fontSize="2rem" left="5rem" top="0.8rem"/>
    
    {/* Top */}
    <div style={{display:"flex", flexDirection:"column",justifyContent:"center",alignItems:"center"}} >
      <div style={{display:"flex", flexDirection:"row",justifyContent:"end"}}>
        <p>잔액</p>
        <button>송금</button>
      </div>
      <div>
        <p>계좌 버튼 ,'복사기능'</p>
      </div>

      <div>
        <button> 연결된 모임보기</button>
      </div>

    </div>
    {/* Top */}







    {/* Mid */}

    <p>사용내역</p>


    <div style={{display:"flex", flexDirection:"column",justifyContent:"center",alignItems:'center',width:"98%", backgroundColor:"white"}}>
      <div style={{display:"flex", flexDirection:"row", justifyContent:"space-between",width:"95%"}}>
        <p>사용월</p>
        <button>열기접기</button>
       
      </div>
      
      
      
      <div>
          여기는 map 함수로 
      </div>


    </div>


    {/* Mid */}


  </div>
  );
};

export default AccountInfo;
