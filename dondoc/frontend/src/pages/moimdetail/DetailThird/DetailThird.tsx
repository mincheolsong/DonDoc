import styles from "./DetailThird.module.css";
import {useState} from 'react'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar } from 'react-chartjs-2';
import { faker } from '@faker-js/faker';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

const labels = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];

const data = {
  labels,
  datasets: [
    {
      label: 'Dataset 1',
      data: labels.map(() => faker.datatype.number({ min: 0, max: 1000 })),
      borderColor: 'rgb(255, 99, 132)',
      backgroundColor: 'rgba(255, 99, 132, 0.5)',
    },
    {
      label: 'Dataset 2',
      data: labels.map(() => faker.datatype.number({ min: 0, max: 1000 })),
      borderColor: 'rgb(53, 162, 235)',
      backgroundColor: 'rgba(53, 162, 235, 0.5)',
    },
  ],
};

const options = {
  indexAxis: 'y' as const,
  elements: {
    bar: {
      borderWidth: 2,
    },
  },
  responsive: true,
  plugins: {
    legend: {
      position: 'right' as const,
    },
    title: {
      display: true,
      text: 'Chart.js Horizontal Bar Chart',
    },
  },
};

function DetailThird() {
  

  const [nowSelected, setNowSelected] = useState<boolean>(true)

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
          <Bar data={data} options={options}/>
          </div>
        </div>

      </div>

      </div>
    </div>
  );
}

export default DetailThird;




