import './cashDisplayer.css'
import axios from 'axios';
import { useState, useEffect } from 'react';
import CashForm from './CashForm';

const CashDisplayer = (props) => {

  const description = props.description
  const jwtToken = props.jwtToken
  const cash = props.cash
  const setCash = props.setCash
  const [showForm, setShowForm] = useState(false);
  const handleClose = () => setShowForm(false);
  const handleShow = () => setShowForm(true);

  useEffect(() => {
    fetchCash()
  }, [])

  const authHeader = {
    headers: { Authorization: `Bearer ${jwtToken}` }
  };

  //fetch cash
  const fetchCash = async () => {
    axios.get('/savings/cash',
      authHeader
    ).then(res => setCash(res.data.amount))
    .catch(err => console.log(err));
  }

  //update cash
  const onUpdate = (newCash) => {
    handleClose()
    axios.put('/savings/cash', newCash,
    authHeader
    ).then(res => setCash(res.data.amount))
    .catch(err => console.log(err));

  }

  return (
    <div>
      {showForm && 
      <CashForm showFrom={handleShow} handleClose={handleClose} onUpdate={onUpdate} cash={cash} />}
      <div className="total-money">
        <br />
        <a> {cash() + " zł"}</a>
      </div>
      <div className="total-underscore">
        <hr style={{margin: 0}} />
        <a>{description}</a>
        <a className='edit-button btn' onClick={handleShow}>Edit</a>
      </div>
    </div>
  )
}

export default CashDisplayer