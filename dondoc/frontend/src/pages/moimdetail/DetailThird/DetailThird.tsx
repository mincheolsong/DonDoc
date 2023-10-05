import styles from "./DetailThird.module.css";
import {useState} from 'react'
import {
  Chart as ChartJS,
  ArcElement,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar } from 'react-chartjs-2';
import { Doughnut } from 'react-chartjs-2';
import { useEffect } from 'react'
import axios from "axios";
import { useSelector } from "react-redux";
import { UserType } from "../../../store/slice/userSlice";
import { BASE_URL } from "../../../constants";
import { useParams } from "react-router-dom";

ChartJS.register(
  CategoryScale,
  ArcElement,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);



function DetailThird() {
  const [nowSelected, setNowSelected] = useState<boolean>(true)
  const [ThisData, setThisData] = useState<number[]>([])
  const [LastData, setLastData] = useState<number[]>([])


  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken
  const {moimId} = useParams()

  useEffect(() => {
    axios.get(`${BASE_URL}/api/moim/detail/${moimId}`,{
      headers:{
        'Authorization': 'Bearer ' + token
      }
    })
    .then((res) => {
      console.log(res.data.response)
      const identificationNumber:string = res.data.response.identificationNumber
      const memberAccountNumber:string = res.data.response.moimAccountNumber
      const moimAccountNumber:string = res.data.response.moimMembers

      const data = {
        identificationNumber: identificationNumber,
        memberAccountNumber: memberAccountNumber,
        moimAccountNumber: moimAccountNumber,
        month:9
      }
      axios.post(`${BASE_URL}/api/moim/mydata/transferAmount`, data, {
        headers: {
        'Authorization': 'Bearer ' + token
        }
      })
      .then((res) => {
        console.log(res.data)
      })
    })
  },[])

  useEffect(() => {
    axios.get(`${BASE_URL}/api/moim/mydata/spendingAmount/1/29`,{
      headers:{
        'Authorization': 'Bearer ' + token
      }
    })
    .then((res) => {
      
      console.log(res.data.response)
      setThisData(res.data.response.thisMonth)
      setLastData(res.data.response.LastMonth)
    })
  },[])

  const labels = ['총합', '쇼핑', '교육', '식비', '여가', '기타'];
  
  const data1 = {
    labels,
    datasets: [
      {
        label: '9월',
        data: LastData,
        borderColor: 'rgb(255, 99, 132)',
        backgroundColor: 'rgba(255, 99, 132, 0.5)',
      },
      {
        label: '10월',
        data: ThisData,
        borderColor: 'rgb(53, 162, 235)',
        backgroundColor: 'rgba(53, 162, 235, 0.5)',
      },
    ],
  };


  const data2 = {
    labels :['총합', '쇼핑', '교육', '식비', '여가', '기타'],
    datasets: [
      {
        label: '# of Votes',
        data: ThisData,
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(255, 159, 64, 0.2)',
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)',
        ],
        borderWidth: 1,
      },
    ],
  };
  
  const options = {
    scales: {
      x: {
        ticks: {
          font: {
            size:6
          }
        }
      },
      y: {
        ticks: {
          font: {
            size:5
          }
        }
      }
    },
    indexAxis: 'x' as const,
    elements: {
      bar: {
        borderWidth: 0.1,
      },
    },
    responsive: false,
    plugins: {
      legend: {
        labels: {
          font: {
            size: 8
          }
        },
        position: 'bottom' as const,
      },
      title: {
        display: true,
        text: '전월 사용 금액 비교',
      },
    },
  };

  const ClickMissionTab = () => {
    setNowSelected(false)
  }
  const ClickMoneyTab = () => {
    setNowSelected(true)
  }

  return (
<div className={styles.container}>
      {/* <BackLogoHeader name="오늘부터 다이어트" fontSize="2rem" left="5rem" top="0.8rem"/> */}
      <div className={styles.content}>

      <div className={styles.requests}>
        <div className={styles.pagetitle}>
          <h1>마이데이터</h1>
        </div>

        <div className={styles.maincontent}>
          <div className={styles.toptab}>
            <div className={styles.requestmoney} onClick={ClickMoneyTab}>
              <div className={styles.icon}>
              </div>
              <div className={styles.requesttext}>
                <p style={{color: nowSelected ? '#7677E8' : '', borderBottom: nowSelected ? '2px solid #7677E8' : ''}}>마이데이터</p>
              </div>
            </div>

            <div className={styles.requestmission} onClick={ClickMissionTab}>
              <div className={styles.icon}>
              </div>
              <div className={styles.requesttext}>
                <p style={{color: !nowSelected ? '#DD7979' : '', borderBottom: !nowSelected ? '2px solid #DD7979' : ''}}>이체 이력</p>
              </div>
            </div>
          </div>


          <div className={styles.requestlist}>
            <div className={styles.Chart}> 

            <Bar data={data1} options={options} style={{height:"50vh", width:"80vw"}}/>
            <Doughnut data={data2} />
            </div>
          </div>
        </div>

      </div>

      </div>
    </div>
  );
}

export default DetailThird;




