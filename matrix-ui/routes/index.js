const express = require('express');
const router = express.Router();
const request = require('request');
const LocalStorage = require('node-localstorage').LocalStorage;
const localStorage = new LocalStorage('./scratch');


const apiOptions = {
  server: 'http://35.195.174.247'
};


/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'The Matrix' });
});

router.get('/all-programs/', function (req, res, next) {
  const requestOptions = {
    url: apiOptions.server + '/architect/programs',
    method: 'GET',
    json: {}
  };
  request(requestOptions, (err, response, body) => {
    if (err) {
      res.render('error', err)
    } else if (response.statusCode === 200) {
      res.render('programs', {title: 'All programs', programs: body})
    } else {
      res.render('error', {})
    }
  });
});

router.get('/fight/:name', function (req, res, next) {
  const requestOptions = {
    url: apiOptions.server + '/agent-' + req.params.name + "/fight",
    method: 'GET',
    encoding: null
  };
  request(requestOptions, (err, response, body) => {
    if (!err && response.statusCode === 200) {
      console.log(JSON.parse(body))
      res.send("" + JSON.parse(body).agentHealth);
    }
  });
});

router.get('/fight/', function (req, res, next) {
  const requestOptions = {
    url: apiOptions.server + '/agent/fight',
    method: 'GET',
    encoding: null
  };
  request(requestOptions, (err, response, body) => {
    if (!err && response.statusCode === 200) {
      res.send(JSON.parse(body).agentHealth);
    }
  });
});


router.get('/agent/', function (req, res, next) {
  const requestOptions = {
    url: apiOptions.server + '/agent/health',
    method: 'GET',
    json: {}
  };
  request(requestOptions, (err, response, body) => {
    if (err) {
      res.render('error', err)
    } else if (response.statusCode === 200) {
      res.render('agent', { title: 'Agent', agent: body.name, agentId: req.params.name, health: body.health });
    } else {
      res.render('error', {})
    }
  });
});

router.get('/agent/:name', function (req, res, next) {
  const requestOptions = {
    url: apiOptions.server + '/agent-' + req.params.name + "/health",
    method: 'GET',
    json: {}
  };
  request(requestOptions, (err, response, body) => {
    if (err) {
      res.render('error', err)
    } else if (response.statusCode === 200) {
      res.render('agent', { title: 'Agent', agent: body.name, agentId: req.params.name, health: body.health });
    } else {
      res.render('error', {})
    }
  });
});

router.get('/agent/image/', async (req, res) => {
  const requestOptions = {
    url: apiOptions.server + "/agent/",
    method: 'GET',
    encoding: null
  };
  request(requestOptions,
      (err, resp, buffer) => {
        if (!err && resp.statusCode === 200){
          res.set("Content-Type", "image/jpeg");
          res.send(resp.body);
        }
      });
});

router.get('/agent/image/:name', async (req, res) => {
  const requestOptions = {
    url: apiOptions.server + "/agent-" + req.params.name,
    method: 'GET',
    encoding: null
  };
  request(requestOptions,
      (err, resp, buffer) => {
        if (!err && resp.statusCode === 200){
          res.set("Content-Type", "image/jpeg");
          res.send(resp.body);
        }
      });
});

router.get('/architect/', function (req, res, next) {
  res.render('architect', { title: 'Architect' });
});

router.get('/oracle/', function (req, res, next) {
  res.render('oracle', { title: 'Oracle' });
});

router.get('/oracle/name/', function (req, res, next) {
  const requestOptions = {
    url: apiOptions.server + "/oracle/name",
    method: 'GET',
    encoding: null
  };
  request(requestOptions, (err, response, body) => {
    if (!err && response.statusCode === 200) {
      localStorage.setItem("name", body);
      res.send(body);
    }
  });
});

router.get('/oracle/prediction/', function (req, res, next) {
  let name = localStorage.getItem("name");
  const requestOptions = {
    url: apiOptions.server + "/oracle/prediction",
    method: 'GET',
    encoding: null,
    headers: {
      'name': name
    }
  };
  request(requestOptions, (err, response, body) => {
    if (!err && response.statusCode === 200) {
      res.send(JSON.parse(body).prediction);
    }
  });
});

router.get('/merovingian/', function (req, res, next) {
  let name = localStorage.getItem("name");
  const requestOptions = {
    url: apiOptions.server + "/merovingian/exiled",
    method: 'GET',
    encoding: null,
    headers: {
      'name': name
    }
  };
  request(requestOptions, (err, response, body) => {
    if (!err && response.statusCode === 200) {
      console.log(body)
      res.render('merovingian', { title: 'Merovingian', exiled: body });
    }
    console.log()
  });
});

module.exports = router;
